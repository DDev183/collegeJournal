package page.danya.controllers;

import jdk.vm.ci.meta.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import page.danya.DTO.AllMarksDTO;
import page.danya.DTO.MarkHistoryDTO;
import page.danya.models.*;
import page.danya.repository.*;
import page.danya.security.jwt.JwtTokenProvider;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@RestController
@CrossOrigin    //VERY VERY IMPORTANT THINGS!!!!
@RequestMapping(value = "/api/student")
public class StudentController {


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

    @Autowired
    private EnglishRepository englishRepository;


    @CrossOrigin( methods = RequestMethod.GET)
    @GetMapping(value = "/getMarkHistory", produces = "application/json")
    public ResponseEntity getMarkHistory(@RequestHeader("Authorization") String token) {
        token = token.substring(7, token.length());



        String username = jwtTokenProvider.getUsername(token);
        String userRole = userRepository.findByUsername(username).get().getRole().get(0).getName();


        if (userRole.equals(RegisterController.ROLE_USER)){


            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");



            List<MarkHistoryDTO> dto = new LinkedList<>();

            markRepository.findFirst15ByStudent(userRepository.findByUsername(username).get()).stream().forEach(
                    mark -> {

                        if (!(mark.getValue() == 0 && mark.getAbsent().getShortname().equalsIgnoreCase(" "))) {

                            String teacher = mark.getLesson().getTeaching().getTeacher().getLastname() + " " + mark.getLesson().getTeaching().getTeacher().getFirstname();
                            String subject = mark.getLesson().getTeaching().getSubject().getName();
                            String absent = mark.getAbsent().getShortname();
                            String time = mark.getTime().format(timeFormatter);
                            String date = mark.getDate().format(dateFormatter);

                            String thisMark = " ";
                            if (mark.getValue() != 0) {
                                thisMark = String.valueOf(mark.getValue());
                            }

                            dto.add(new MarkHistoryDTO(thisMark, teacher, subject, absent, date, time));
                        }
                    }
            );



            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(403).build();

        }

    }


    @CrossOrigin( methods = RequestMethod.GET)
    @GetMapping(value = "/getAllMarks", produces = "application/json")
    public ResponseEntity getAllMarks(@RequestHeader("Authorization") String token, @RequestParam String startDate, @RequestParam String endDate) {
        token = token.substring(7, token.length());

        boolean flag;

        if (startDate.equalsIgnoreCase("null") || endDate.equalsIgnoreCase("null")){
            flag = true;
        } else {
            flag = false;
        }


        String username = jwtTokenProvider.getUsername(token);
        String userRole = userRepository.findByUsername(username).get().getRole().get(0).getName();


        if (userRole.equals(RegisterController.ROLE_USER)){

            DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd.MM.YYYY");




            AllMarksDTO dto = new AllMarksDTO();
            List<LocalDate> dates = new ArrayList<>();
            List<Subject> subjects = new ArrayList<>();
            List<String> stringSubjects = new ArrayList<>();
            List<List<String>> finalMarks = new ArrayList<>();
            List<List<String>> finalAbsents = new ArrayList<>();
            List<String> finalDates = new ArrayList<>();

            APP_User student = userRepository.findByUsername(username).get();



            teachingRepository.findByGroup(groupRepository.findByName(student.getGroup().getName()).get()).stream().forEach(
                    teaching -> {

                        Subject subject = subjectRepository.findByName(teaching.getSubject().getName()).get();
                        if (!subjects.contains(subject)){
                            subjects.add(subject);
                            stringSubjects.add(subject.getName());
                        }

                    }
            );


            List<Mark> marks = markRepository.findByStudent(student);



            marks.stream().filter(
                    mark -> {
                        LocalDate date = mark.getDate();

                        if (flag == false) {
                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
                            System.out.println(startDate);
                            LocalDate startDateField = LocalDate.parse(startDate, dateTimeFormatter);
                            LocalDate endDateField = LocalDate.parse(endDate, dateTimeFormatter);
                            System.out.println("HELP!!!");

                            System.out.println(date.compareTo(startDateField));
                            System.out.println(date.compareTo(endDateField));

                            boolean res1 = date.compareTo(startDateField) >= 0;
                            boolean res2 = date.compareTo(endDateField) <= 0;

                            if (res1 && res2) {
                                System.out.println();
                                dates.add(date);
                                finalDates.add(date.format(dateFormatter));
                                return true;
                            } else {
                                return false;
                            }
                        } else {
                            dates.add(date);
                            finalDates.add(date.format(dateFormatter));
                            return true;
                        }
                    }
            ).forEach(
                    mark -> {
                        LocalDate date = mark.getDate();




                        List<String> markListForExport = new ArrayList<>();
                        List<String> absentListForExport = new ArrayList<>();

                        subjects.stream().forEach(
                                subject -> {



                                    //Search mark by Date(date taken from list of Dates) and by Student + Subject


                                    if (teachingRepository.findBySubject(subject).isPresent() && lessonRepository.findByTeachingAndDate(teachingRepository.findBySubject(subject).get(), date).isPresent()) {
                                        Lesson lesson = lessonRepository.findByTeachingAndDate(teachingRepository.findBySubject(subject).get(), date).get();


                                        if (markRepository.findByLessonAndStudent(lesson, student).isPresent()) {
                                            Mark thisMark = markRepository.findByLessonAndStudent(lessonRepository.findByTeachingAndDate(teachingRepository.findBySubject(subject).get(), date).get(), student).get();


                                            String thisValue = " ";
                                            if (mark.getValue() != 0) {
                                                thisValue = String.valueOf(mark.getValue());
                                            }

                                            markListForExport.add(thisValue + " "+ thisMark.getAbsent().getShortname());
                                            absentListForExport.add(thisMark.getAbsent().getShortname());

                                        }
                                    } else {
                                        markListForExport.add(" ");
                                        absentListForExport.add(" ");
                                    }


                                }
                        );

                        finalMarks.add(markListForExport);
                        finalAbsents.add(absentListForExport);
                    }
            );



            //TODO: Need to reverse dates!!!
            dto.setDates(finalDates);
            dto.setAbsents(finalAbsents);
            dto.setSubjects(stringSubjects);
            dto.setMarks(finalMarks);




            //TODO: Maybe work incorrectly
            DateTimeFormatter dateFormatterSpec = DateTimeFormatter.ofPattern("YYYY.MM.dd");
            System.out.println("StartDate: " + dates.get(0).format(dateFormatterSpec));
            dto.setStartDate(dates.get(0).format(dateFormatterSpec));
            System.out.println("EndDate: " + dates.get(dates.size() - 1).format(dateFormatterSpec));
            dto.setEndDate(dates.get(dates.size() - 1).format(dateFormatterSpec));





            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(403).build();

        }
    }

}
