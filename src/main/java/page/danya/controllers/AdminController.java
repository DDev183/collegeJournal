package page.danya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import page.danya.DTO.EnglishDependentDTO;
import page.danya.DTO.findByLastAndFirst;
import page.danya.models.*;
import page.danya.repository.*;
import page.danya.security.jwt.JwtTokenProvider;

import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@RestController
@CrossOrigin()    //VERY VERY IMPORTANT THINGS!!!!
@RequestMapping(value = "/api/admin")
public class AdminController {


    @Autowired
    private APP_UserRepository userRepository;

    @Autowired
    private TeachingRepository teachingRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private AbsentRepository absentRepository;

    @Autowired
    private EnglishRepository englishRepository;


    @CrossOrigin( methods = RequestMethod.POST)
    @PostMapping(value = "/init", produces = "application/json")
    public ResponseEntity init(@RequestHeader("Authorization") String token){
        token = token.substring(7, token.length());

        String username = jwtTokenProvider.getUsername(token);
        String role = userRepository.findByUsername(username).get().getRole().get(0).getName();

        if (role.equals(RegisterController.ROLE_ADMIN)){

            if (!absentRepository.findByShortname("НБ").isPresent()){
                Absent absent = new Absent("Отсутствует", "НБ");
                absentRepository.save(absent);
            }
            if (!absentRepository.findByShortname("УП").isPresent()){
                Absent absent = new Absent("Уважительный пропуск", "УП");
                absentRepository.save(absent);
            }
            if (!absentRepository.findByShortname("НП").isPresent()){
                Absent absent = new Absent("Неуважительный пропуск", "НП");
                absentRepository.save(absent);
            }
            if (!absentRepository.findByName("Присутствует").isPresent()){
                Absent absent = new Absent("Присутствует", " ");
                absentRepository.save(absent);
            }
            if (!englishRepository.findById(10).isPresent()){  //Default englishDependent
                EnglishDependent englishDependent = new EnglishDependent(10);
                englishRepository.save(englishDependent);
            }

            if (!groupRepository.findById(10).isPresent()){
                groupRepository.save(new Group(10, "Default group"));
            }



            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).build();

        }

    }


    @CrossOrigin( methods = RequestMethod.POST)
    @PostMapping(value = "/createGroup", produces = "application/json")
    public ResponseEntity createGroup(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> body){
        token = token.substring(7, token.length());
        System.out.println(body.get("Group"));
        String group = body.get("Group");

        String username = jwtTokenProvider.getUsername(token);
        String role = userRepository.findByUsername(username).get().getRole().get(0).getName();

        if (role.equals(RegisterController.ROLE_ADMIN)){

            if (groupRepository.findByName(group).isPresent()){
                return ResponseEntity.status(400).build();
            }

            groupRepository.save(new Group(group));


            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).build();

        }

    }




    @CrossOrigin(methods = RequestMethod.GET)
    @GetMapping(value = "/getAllLastname", produces = "application/json")
    public ResponseEntity getAllLastname(@RequestHeader("Authorization") String token){
        token = token.substring(7, token.length());

        String username = jwtTokenProvider.getUsername(token);

        String role = userRepository.findByUsername(username).get().getRole().get(0).getName();

        if (role.equals(RegisterController.ROLE_ADMIN)) {


            List<APP_User> users = userRepository.findAll();


            List<String> result = new ArrayList<>();

            users.forEach(userF -> result.add(userF.getLastname()));

//            System.out.println(result.toArray().toString());


            return ResponseEntity.ok(result);

        } else {
            return ResponseEntity.status(403).build();
        }
    }




    @CrossOrigin(methods = RequestMethod.GET)
    @GetMapping(value = "/getAllFirstname", produces = "application/json")
    public ResponseEntity getAllFirstname(@RequestHeader("Authorization") String token){
        token = token.substring(7, token.length());

        String username = jwtTokenProvider.getUsername(token);
        String role = userRepository.findByUsername(username).get().getRole().get(0).getName();

        if (role.equals(RegisterController.ROLE_ADMIN)) {


            List<APP_User> users = userRepository.findAll();

            List<String> result = new ArrayList<>();

            users.forEach(userF -> result.add(userF.getFirstname()));

//            System.out.println(result.toArray().toString());


            return ResponseEntity.ok(result);

        } else {
            return ResponseEntity.status(403).build();
        }
    }



    @CrossOrigin(methods = RequestMethod.GET)
    @GetMapping(value = "/getAllMiddlename", produces = "application/json")
    public ResponseEntity getAllMiddlename(@RequestHeader("Authorization") String token){
        token = token.substring(7, token.length());

        String username = jwtTokenProvider.getUsername(token);
        String role = userRepository.findByUsername(username).get().getRole().get(0).getName();

        if (role.equals(RegisterController.ROLE_ADMIN)) {


            List<APP_User> users = userRepository.findAll();

            List<String> result = new ArrayList<>();

            users.forEach(userF -> {
                //TODO: Renove dublicates!!!
                result.add(userF.getMiddlename());

            });

//            System.out.println(result.toArray().toString());


            return ResponseEntity.ok(result);

        } else {
            return ResponseEntity.status(403).build();
        }
    }


    @CrossOrigin( methods = RequestMethod.POST)
    @PostMapping(value = "/getUserByLastnameAndFirstname", produces = "application/json")
    public ResponseEntity getUserByLastnameAndFirstname(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> body){
        token = token.substring(7, token.length());
//        System.out.println(body.get("Group"));
        String firstname = body.get("Firstname");
        String lastname = body.get("Lastname");


        String username = jwtTokenProvider.getUsername(token);
        String role = userRepository.findByUsername(username).get().getRole().get(0).getName();

        if (role.equals(RegisterController.ROLE_ADMIN)){


//            Map<Integer, findByLastAndFirst> users = new HashMap<>();
//
//            userRepository.findByFirstnameAndLastname(firstname, lastname).forEach(user -> {
//
//                int i = 0;
//                findByLastAndFirst userF = new findByLastAndFirst(user.getId(), user.getFirstname(), user.getLastname(), user.getMiddlename(), user.getGroup().getName(), user.getUsername());
//
//                users.put(i++, userF);
//
//            });

            List<findByLastAndFirst> users = new ArrayList<>();

            userRepository.findByFirstnameAndLastname(firstname, lastname).forEach(
                    user -> {
                        users.add(new findByLastAndFirst(user.getId(), user.getFirstname(), user.getLastname(), user.getMiddlename(), user.getGroup().getName(), user.getUsername(), user.getRole().get(0).getName()));
                    }
            );


            return ResponseEntity.ok(users);
        } else {
            return ResponseEntity.status(403).build();

        }

    }


    @CrossOrigin(methods = RequestMethod.GET)
    @GetMapping(value = "/getAllGroups", produces = "application/json")
    public ResponseEntity getAllGroups(@RequestHeader("Authorization") String token) {
        token = token.substring(7, token.length());

        String username = jwtTokenProvider.getUsername(token);
        String role = userRepository.findByUsername(username).get().getRole().get(0).getName();


        if (role.equals(RegisterController.ROLE_ADMIN)) {

            List<String> res = new ArrayList<>();

            groupRepository.findAll().forEach(group -> {
                res.add(group.getName());
            });


            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(403).build();

        }
    }



    @CrossOrigin( methods = RequestMethod.POST)
    @PostMapping(value = "/linkStudentWithGroup", produces = "application/json")
    public ResponseEntity linkStudentWithGroup(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> body){
        token = token.substring(7, token.length());
//        System.out.println(body.get("Group"));
        String userId = body.get("userId");
        String groupName = body.get("group");


        String username = jwtTokenProvider.getUsername(token);
        String role = userRepository.findByUsername(username).get().getRole().get(0).getName();

        if (role.equals(RegisterController.ROLE_ADMIN)){


            APP_User user = userRepository.findById(Integer.parseInt(userId)).get();

            Group group = groupRepository.findByName(groupName).get();
            user.setGroup(group);

            userRepository.save(user);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).build();

        }

    }


    @CrossOrigin( methods = RequestMethod.POST)
    @PostMapping(value = "/createSubject", produces = "application/json")
    public ResponseEntity createSubject(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> body){
        token = token.substring(7, token.length());
//        System.out.println(body.get("Group"));
        String subjectName = body.get("subject");


        String username = jwtTokenProvider.getUsername(token);
        String role = userRepository.findByUsername(username).get().getRole().get(0).getName();

        if (role.equals(RegisterController.ROLE_ADMIN)){


            if (subjectRepository.findByName(subjectName).isPresent()){
                ResponseEntity.status(400).build();
            }


            Subject subject = new Subject(subjectName);

            subjectRepository.save(subject);


            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).build();

        }

    }


    @CrossOrigin(methods = RequestMethod.GET)
    @GetMapping(value = "/getAllRoles", produces = "application/json")
    public ResponseEntity getAllRoles(@RequestHeader("Authorization") String token) {
        token = token.substring(7, token.length());

        String username = jwtTokenProvider.getUsername(token);
        String userRole = userRepository.findByUsername(username).get().getRole().get(0).getName();


        if (userRole.equals(RegisterController.ROLE_ADMIN)) {

            List<String> res = new ArrayList<>();

            roleRepository.findAll().forEach(role -> {
                res.add(role.getName());
            });


            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(403).build();

        }
    }


    @CrossOrigin( methods = RequestMethod.POST)
    @PostMapping(value = "/changeRole", produces = "application/json")
    public ResponseEntity changeRole(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> body){
        token = token.substring(7, token.length());
//        System.out.println(body.get("Group"));
        String userId = body.get("userId");
        String roleName = body.get("role");


        String username = jwtTokenProvider.getUsername(token);
        String userRole = userRepository.findByUsername(username).get().getRole().get(0).getName();

        if (userRole.equals(RegisterController.ROLE_ADMIN)){


            APP_User user = userRepository.findById(Integer.parseInt(userId)).get();

            List<Role> role = roleRepository.findByName(roleName);
            user.setRole(role);

            userRepository.save(user);

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).build();

        }

    }


    @CrossOrigin(methods = RequestMethod.GET)
    @GetMapping(value = "/getAllSubjects", produces = "application/json")
    public ResponseEntity getAllSubjects(@RequestHeader("Authorization") String token) {
        token = token.substring(7, token.length());

        String username = jwtTokenProvider.getUsername(token);
        String userRole = userRepository.findByUsername(username).get().getRole().get(0).getName();


        if (userRole.equals(RegisterController.ROLE_ADMIN)) {

            List<String> res = new ArrayList<>();

            subjectRepository.findAll().forEach(subject -> {
                res.add(subject.getName());
            });


            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(403).build();

        }
    }


    @CrossOrigin(methods = RequestMethod.GET)
    @GetMapping(value = "/getAllTeachers", produces = "application/json")
    public ResponseEntity getAllTeachers(@RequestHeader("Authorization") String token) {
        token = token.substring(7, token.length());

        String username = jwtTokenProvider.getUsername(token);
        String userRole = userRepository.findByUsername(username).get().getRole().get(0).getName();


        if (userRole.equals(RegisterController.ROLE_ADMIN)) {

            List<String> res = new ArrayList<>();

            Role role = roleRepository.findByName(RegisterController.ROLE_TEACHER).get(0);

            userRepository.findByRoles(role).forEach(user -> {
                res.add(user.getLastname() + " " + user.getFirstname());
            });


            return ResponseEntity.ok(res);
        } else {
            return ResponseEntity.status(403).build();

        }
    }

    @CrossOrigin( methods = RequestMethod.POST)
    @PostMapping(value = "/linkTSG", produces = "application/json")
    public ResponseEntity linkTSG(@RequestHeader("Authorization") String token, @RequestBody Map<String, String> body){
        token = token.substring(7, token.length());
//        System.out.println(body.get("Group"));
        String subjectName = body.get("subject");
        String groupName = body.get("group");
        String teacherLastAndFirstname = body.get("teacher");

        Subject subject = subjectRepository.findByName(subjectName).get();
        Group group = groupRepository.findByName(groupName).get();
        String[] array = teacherLastAndFirstname.split(" ");
        String lastname = array[0];
        String firstname = array[1];

        APP_User teacher = userRepository.findByFirstnameAndLastnameAndRoles(firstname, lastname, roleRepository.findByName(RegisterController.ROLE_TEACHER).get(0)).get();






        String username = jwtTokenProvider.getUsername(token);
        String userRole = userRepository.findByUsername(username).get().getRole().get(0).getName();

        if (userRole.equals(RegisterController.ROLE_ADMIN)){

            teachingRepository.save(new Teaching(group, subject, teacher));

            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).build();

        }

    }



    @CrossOrigin( methods = RequestMethod.GET)
    @GetMapping(value = "/getsStudentsFromGroup", produces = "application/json")
    public ResponseEntity getStudentsFromGroupGet(@RequestHeader("Authorization") String token, @RequestParam String group, @RequestParam String teacher, @RequestParam String subject){
        token = token.substring(7, token.length());
//        System.out.println(body.get("Group"));

        System.out.println(group + "\n" + teacher + "\n" + subject);



        Group thisGroup = groupRepository.findByName(group).get();
        Subject thisSubject = subjectRepository.findByName(subject).get();

        String[] array = teacher.split(" ");
        APP_User thisTeacher = userRepository.findByFirstnameAndLastnameAndRoles(array[1], array[0], roleRepository.findByName(RegisterController.ROLE_TEACHER).get(0)).get();



        String username = jwtTokenProvider.getUsername(token);
        String userRole = userRepository.findByUsername(username).get().getRole().get(0).getName();


        if (userRole.equals(RegisterController.ROLE_ADMIN)){


            if (!englishRepository.findByGroupAndSubjectAndTeacher(thisGroup, thisSubject, thisTeacher).isPresent()){
                englishRepository.save(new EnglishDependent(thisTeacher, thisSubject, thisGroup));
            }

            EnglishDependent englishDependent = englishRepository.findByGroupAndSubjectAndTeacher(thisGroup, thisSubject, thisTeacher).get();
            List<APP_User> studentsList = userRepository.findByGroup(englishDependent.getGroup());

            List<APP_User> students = userRepository.findByGroup(thisGroup);
            List<String> studentsName = new ArrayList<>();
            List<String> value = new ArrayList<>();

            students.stream().forEach(e -> {
                AtomicBoolean flag = new AtomicBoolean(false);
                studentsName.add(e.getLastname() + " " + e.getFirstname());
                studentsList.stream().forEach(k -> {
                    if (k.getId() == e.getId()){
                        flag.set(true);
                        value.add("+");
                    }
                });

                if (flag.get() == false) value.add(" ");
            });

            EnglishDependentDTO dto = new EnglishDependentDTO();
            dto.setStudents(studentsName);
            dto.setValue(value);







            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.status(403).build();

        }

    }



    @CrossOrigin( methods = RequestMethod.POST)
    @PostMapping(value = "/englishDependent", produces = "application/json")
    public ResponseEntity englishDependent(@RequestHeader("Authorization") String token, @RequestBody EnglishDependentDTO dto) throws InterruptedException {
        token = token.substring(7, token.length());

        String username = jwtTokenProvider.getUsername(token);
        String role = userRepository.findByUsername(username).get().getRole().get(0).getName();

        Group thisGroup = groupRepository.findByName(dto.getGroup()).get();
        Subject thisSubject = subjectRepository.findByName(dto.getSubject()).get();
        APP_User thisTeacher = userRepository.findByUsername(username).get();


        if (role.equals(RegisterController.ROLE_ADMIN)){


            if (!englishRepository.findByGroupAndSubjectAndTeacher(thisGroup, thisSubject, thisTeacher).isPresent()){
                englishRepository.save(new EnglishDependent(thisTeacher, thisSubject, thisGroup));
            }

                EnglishDependent englishDependent = englishRepository.findByGroupAndSubjectAndTeacher(thisGroup, thisSubject, thisTeacher).get();
                EnglishDependent defaultEnglishDependent = englishRepository.findById(10).get();
//                englishRepository.delete(englishDependent);



            List<String> students = dto.getStudents();
                List<String> values = dto.getValue();

                List<APP_User> studentsList = new ArrayList<>();

                Map<String, String> map = IntStream.range(0, students.size())
                        .boxed()
                        .collect(Collectors.toMap(i -> students.get(i), i -> values.get(i)));


                map.entrySet().stream()
                        .filter(e -> e.getValue().equalsIgnoreCase("+"))
                        .forEach(e -> {
                        String[] array = e.getKey().split(" ");
                            System.out.println(array[0]);
                            APP_User student = userRepository.findByLastnameAndFirstnameAndGroup(array[0], array[1], thisGroup).get();
                        studentsList.add(student);
                        student.setEnglishDependent(englishDependent);
                        userRepository.save(student);
                });

            map.entrySet().stream()
                    .filter(e -> e.getValue().equalsIgnoreCase(" "))
                    .forEach(e -> {
                        String[] array = e.getKey().split(" ");
                        System.out.println(array[0]);
                        APP_User student = userRepository.findByLastnameAndFirstnameAndGroup(array[0], array[1], thisGroup).get();
                        studentsList.add(student);
                        student.setEnglishDependent(defaultEnglishDependent);
                        userRepository.save(student);
                    });

//                englishDependent.setStudents(studentsList);
//                System.out.println(englishDependent.getStudents().size());
//
//                englishRepository.saveAndFlush(englishDependent);





                return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.status(403).build();

        }

    }


}