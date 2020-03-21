package page.danya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import page.danya.config.WebSecurityConfig;
import page.danya.models.APP_User;
import page.danya.models.Group;
import page.danya.DTO.Person;
import page.danya.models.Role;
import page.danya.repository.APP_UserRepository;
import page.danya.repository.GroupRepository;
import page.danya.repository.RoleRepository;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@RestController
public class RegisterController {


    final static String ROLE_USER = "ROLE_USER";
    final static String ROLE_TEACHER = "ROLE_TEACHER";
    final static String ROLE_ADMIN = "ROLE_ADMIN";

    @Autowired
    private APP_UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private WebSecurityConfig webSecurityConfig;

    @Autowired
    private RoleRepository roleRepository;

//    @GetMapping("/registration")
//    public String register(Model model){
//        System.out.println("Test");
//        model.addAttribute("registerForm", new APP_User());
//        return "registration";
//    }



    void rolesInit(){

        Iterator<Role> roleIterator = roleRepository.findAll().listIterator();

        while (roleIterator.hasNext()) {
            if (roleIterator.next().getName().equalsIgnoreCase(ROLE_USER)) {
                roleRepository.save(new Role(ROLE_USER));
            }
            if (roleIterator.next().getName().equalsIgnoreCase(ROLE_TEACHER)) {
                roleRepository.save(new Role(ROLE_TEACHER));
            }
            if (roleIterator.next().getName().equalsIgnoreCase(ROLE_ADMIN)) {
                roleRepository.save(new Role(ROLE_ADMIN));
            }
        }

    }

    @CrossOrigin    //VERY VERY IMPORTANT THINGS!!!!
    @PostMapping(value = "/registration", consumes = "application/json")
    public ResponseEntity addUser(@RequestBody Person person) throws URISyntaxException {


        System.out.println("Data's size= " + person.toString());


        if (groupRepository.findById(1).isPresent() == false){
            Group group = new Group();
            group.setId(1);
            group.setName("Default group");

            group = groupRepository.save(group);
        }



        if (userRepository.findByUsername(person.getUsername()).isPresent()){
            return ResponseEntity.status(401).build();
        }

            APP_User user = new APP_User();


            rolesInit();

            user.setFirstname(person.getFirstname());
            user.setLastname(person.getLastname());
            user.setMiddlename(person.getMiddlename());
            user.setPassword(webSecurityConfig.passwordEncoder().encode(person.getPassword()));
            user.setTelnumber(person.getTelnumber());
            user.setEmail(person.getEmail());
            user.setUsername(person.getUsername());
            user.setGroup(groupRepository.findById(1).get());  //set default value

            List<Role> roles = roleRepository.findByName(ROLE_USER);
            user.setRole(roles);

            user = userRepository.save(user);


            System.out.println("User= " + user.getUsername() + " had been registered"); //Log

            return ResponseEntity.status(200).build();

    }





//    @PostMapping("/login")
//    public String login(@ModelAttribute(name = "loginForm") UserService user, Model model){
//
//        webSecurityConfig.configure();
//
//        return "hello";
//    }


    @GetMapping("/login")
    public String getLoginPage(Model model){
        return "login";
    }

}
