package page.danya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import page.danya.DAO.ProfileDAO;
import page.danya.DTO.ProfileDTO;
import page.danya.DTO.findByLastAndFirst;
import page.danya.models.APP_User;
import page.danya.models.Group;
import page.danya.models.Role;
import page.danya.models.Subject;
import page.danya.repository.APP_UserRepository;
import page.danya.repository.GroupRepository;
import page.danya.repository.RoleRepository;
import page.danya.repository.SubjectRepository;
import page.danya.security.jwt.JwtTokenProvider;

import java.util.*;

@RestController
@CrossOrigin()    //VERY VERY IMPORTANT THINGS!!!!
@RequestMapping(value = "/api/admin")
public class AdminController {


    @Autowired
    private APP_UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private SubjectRepository subjectRepository;


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


}