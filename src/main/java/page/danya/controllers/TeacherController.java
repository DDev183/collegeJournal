package page.danya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import page.danya.DAO.createAbsentDAO;
import page.danya.DTO.TeacherMarkDTO;
import page.danya.DTO.createLessonDTO;
import page.danya.models.*;
import page.danya.repository.*;
import page.danya.security.jwt.JwtTokenProvider;

import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@CrossOrigin()    //VERY VERY IMPORTANT THINGS!!!!
@RequestMapping("/api/teacher")
public class TeacherController {

    @Autowired
    private APP_UserRepository userRepository;

    @Autowired
    private TeachingRepository teachingRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private AbsentRepository absentRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private LessonRepository lessonRepository;

    @Autowired
    private MarkRepository markRepository;



//    @CrossOrigin
//    @GetMapping("/test")
//    String test(){
//        return "Эта зараза работает";
//    }


    @CrossOrigin(methods = RequestMethod.GET)
    @GetMapping(value = "/getAllAbsent", produces = "application/json")
    public ResponseEntity getAllAbsent(@RequestHeader("Authorization") String token) {
        token = token.substring(7, token.length());

        String username = jwtTokenProvider.getUsername(token);
        String userRole = userRepository.findByUsername(username).get().getRole().get(0).getName();


        if (userRole.equals(RegisterController.ROLE_TEACHER)) {

            List<String> res = new ArrayList<>();

//            absentRepository.findAll().forEach(absent -> {
//                System.out.println(absent.getShortname());
//                res.add(absent.getShortname());
//            });
            res.add(absentRepository.findByShortname("НБ").get().getShortname());
            res.add(absentRepository.findByShortname(" ").get().getShortname());

            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(403).build();

        }
    }


    @CrossOrigin(methods = RequestMethod.GET)
    @GetMapping(value = "/getAvailableGroups", produces = "application/json")
    public ResponseEntity getAvailableGroups(@RequestHeader("Authorization") String token) {
        token = token.substring(7, token.length());

        String username = jwtTokenProvider.getUsername(token);
        String userRole = userRepository.findByUsername(username).get().getRole().get(0).getName();


        if (userRole.equals(RegisterController.ROLE_TEACHER)) {

            List<String> res = new ArrayList<>();

            teachingRepository.findByTeacher(userRepository.findByUsername(username).get()).forEach(teaching -> {
                res.add(teaching.getGroup().getName());
            });


            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(403).build();

        }
    }



    @CrossOrigin( methods = RequestMethod.POST)
    @PostMapping(value = "/getAvailableSubjects", produces = "application/json")
    public ResponseEntity getAvailableSubjects(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> body){
        token = token.substring(7, token.length());
//        System.out.println(body.get("Group"));
        String groupName = body.get("group");

        Group group = groupRepository.findByName(groupName).get();


        String username = jwtTokenProvider.getUsername(token);
        String userRole = userRepository.findByUsername(username).get().getRole().get(0).getName();

        if (userRole.equals(RegisterController.ROLE_TEACHER)){

            List<String> res = new ArrayList<>();

            APP_User teacher = userRepository.findByUsername(username).get();

            List<Teaching> teachingList = teachingRepository.findByTeacherAndGroup(teacher, group);

            teachingList.forEach(teaching ->{
                res.add(teaching.getSubject().getName());
            });




            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(403).build();

        }

    }


    @CrossOrigin( methods = RequestMethod.POST)
    @PostMapping(value = "/getStudentsFromGroup", produces = "application/json")
    public ResponseEntity getStudentsFromGroup(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> body){
        token = token.substring(7, token.length());
//        System.out.println(body.get("Group"));
        String groupName = body.get("group");
        String subjectName = body.get("subject");
        //TODO: ADD ENGLISH subGROUPS

        Group group = groupRepository.findByName(groupName).get();


        String username = jwtTokenProvider.getUsername(token);
        String userRole = userRepository.findByUsername(username).get().getRole().get(0).getName();

        if (userRole.equals(RegisterController.ROLE_TEACHER)){

            List<String> res = new ArrayList<>();





            userRepository.findByGroup(group).forEach(student ->{
                res.add(student.getLastname() + " " + student.getFirstname());
            });




            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(403).build();

        }

    }



    @CrossOrigin( methods = RequestMethod.POST)
    @PostMapping(value = "/createLesson", produces = "application/json")
    public ResponseEntity createLesson(@RequestHeader("Authorization") String token, @RequestBody createLessonDTO body){
        token = token.substring(7, token.length());
//        System.out.println(body.get("Group"));
//        String groupName = body.get("group");
//        String subjectName = body.get("subject");

        String subjectName = body.getSubject();
        String groupName = body.getGroup();
        Group group = groupRepository.findByName(groupName).get();
        System.out.println(groupName);
        System.out.println(subjectName);


        Map<String, String> inputStudents = body.getStudents();
        List<APP_User> absentStudents = new ArrayList<>();


        inputStudents.entrySet().stream()
                .filter(e -> "НБ".equalsIgnoreCase(e.getValue()))
                .forEach(e -> {
                    String[] array = e.getKey().split(" ");
                    String lastname = array[0];
                    String firstname = array[1];
                    APP_User student = userRepository.findByLastnameAndFirstnameAndGroup(lastname, firstname, group).get();
                    absentStudents.add(student);
                });



        String username = jwtTokenProvider.getUsername(token);
        String userRole = userRepository.findByUsername(username).get().getRole().get(0).getName();

        if (userRole.equals(RegisterController.ROLE_TEACHER)){

            //TODO: Be important to English lessons
            Teaching teaching = teachingRepository.findByTeacherAndGroup(userRepository.findByUsername(username).get(), groupRepository.findByName(groupName).get()).get(0);
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

            Lesson lesson = new Lesson(LocalDate.now(), teaching);
            lessonRepository.save(lesson);
            List<APP_User> students = userRepository.findByGroup(groupRepository.findByName(groupName).get());

            students.stream()
                    .filter(e -> absentStudents.contains(e))
                    .forEach(e ->{
                        Mark mark = new Mark(absentRepository.findByShortname("НБ").get(), e, lesson);
                        System.out.println(mark.toString());
                        markRepository.save(mark);
                    });


            students.stream()
                    .filter(e -> !absentStudents.contains(e))
                    .forEach(e -> {
                        Mark mark = new Mark(absentRepository.findByName("Присутствует").get(), e, lesson);
                        System.out.println(mark.toString());
                        markRepository.save(mark);
                    });



            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).build();

        }

    }


    @CrossOrigin( methods = RequestMethod.POST)
    @PostMapping(value = "/getTeacherJournal", produces = "application/json")
    public ResponseEntity getTeacherJournal(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> body){
        token = token.substring(7, token.length());
//        System.out.println(body.get("Group"));
        String groupName = body.get("group");
        String subjectName = body.get("subject");
        //TODO: ADD ENGLISH subGROUPS

        Group group = groupRepository.findByName(groupName).get();



        String username = jwtTokenProvider.getUsername(token);
        APP_User teacher = userRepository.findByUsername(username).get();
        Subject subject = subjectRepository.findByName(subjectName).get();
        String userRole = userRepository.findByUsername(username).get().getRole().get(0).getName();

        if (userRole.equals(RegisterController.ROLE_TEACHER)){

            TeacherMarkDTO res = new TeacherMarkDTO();
            List<APP_User> studentsList = userRepository.findByGroup(group);

            List<String> students = new ArrayList<>();
            Map<LocalDate, List<String>> marks = new HashMap<>();



            studentsList.stream()
            .forEach(e -> {
                students.add(e.getLastname() + " " + e.getFirstname());
            });
            res.setStudents(students);


            Teaching teaching = teachingRepository.findByTeacherAndGroupAndSubject(teacher, group, subject).get();
            List<LocalDate> dates = new ArrayList<>();
            lessonRepository.findByTeaching(teaching).stream()
                    .forEach(e -> {
                        dates.add(e.getDate());
                    });

            dates.stream()
                    .forEach(date -> {


                        System.out.println("++++++++++++++++");
                        System.out.println(dates.size());
                        System.out.println("++++++++++++++++");

                        List<String> markWithAbsent = new ArrayList<>();
                        Lesson lesson = lessonRepository.findByTeachingAndDate(teaching, date).get();
//                        System.out.println(lesson.getDate() + " " + lesson.getTeaching().getSubject());
                        studentsList.stream()
                                .forEach(student -> {
                                    Mark mark = markRepository.findByLessonAndStudent(lesson, student).get();
                                    String value;
                                    String absent;
                                    if (mark.getValue() == 0){
                                        value = "";
                                    } else if (mark.getValue() == 1 || mark.getValue() == 2 || mark.getValue() == 3 || mark.getValue() == 4 || mark.getValue() == 5){
                                        value = String.valueOf(mark.getValue());
                                    } else {
                                     value = "";
                                    }


                                    if (mark.getAbsent().getShortname().equalsIgnoreCase(" ")){
                                        absent = "";
                                    } else {
                                        absent = mark.getAbsent().getShortname();
                                    }

                                    if (absent.equalsIgnoreCase("") && value.equalsIgnoreCase("")) {
                                        markWithAbsent.add(" ");
                                    } else if (!absent.equalsIgnoreCase("") && !value.equalsIgnoreCase("")){
                                        markWithAbsent.add(value + " " + absent);
                                    }
                                    else {
                                        markWithAbsent.add(value + absent);
                                    }
                                });
                        marks.put(date, markWithAbsent);
                    });
            res.setMarks(marks);




            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(403).build();

        }

    }



}
