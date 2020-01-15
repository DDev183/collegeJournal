package page.danya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import page.danya.config.WebSecurityConfig;
import page.danya.models.APP_User;
import page.danya.models.Group;
import page.danya.models.Role;
import page.danya.repository.APP_UserRepository;
import page.danya.repository.GroupRepository;
import page.danya.repository.RoleRepository;
import page.danya.service.UserService;

import java.util.*;

@Controller
public class RegisterController {


    final String ROLE_USER = "ROLE_USER";

    @Autowired
    private APP_UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private WebSecurityConfig webSecurityConfig;

    @Autowired
    private RoleRepository roleRepository;

    @GetMapping("/registration")
    public String register(Model model){
        System.out.println("Test");
        model.addAttribute("registerForm", new APP_User());
        return "registration";
    }


    private Role createRoleIfNotFound(String name) {

        Role role = roleRepository.findByName(name);
        if (role == null) {
            role = new Role(name);
            roleRepository.save(role);
        }
        return role;
    }


    @PostMapping("/registration")
    public String addUser(@ModelAttribute(name = "registerForm") APP_User userData, Model model){


        // Checking...

        if (groupRepository.findById(1).isPresent() == false){
            Group group = new Group();
            group.setId(1);
            group.setName("Default group");

            group = groupRepository.save(group);
        }



//        System.out.println("POSTMAPPING");
//        System.out.println(userData.getFirstname() + "\n" + userData.getLastname() + "\n" + userData.getUsername() + "\n" + userData.getPassword() + "\n" + userData.getEmail() + "\n" + userData.getTelNumber());

//        if (userRepository.findByUsername(userData.getUsername()).get() != null){
//            model.addAttribute("message", "User already created!");
//            return "registration";
//        }

        APP_User user = new APP_User();

        Role role = roleRepository.findByName(ROLE_USER);
        if (role == null) {
            role = new Role(ROLE_USER);
            roleRepository.save(role);
        }


        user.setFirstname(userData.getFirstname());
        user.setLastname(userData.getLastname());
        user.setMiddlename(userData.getMiddlename());
        user.setPassword(webSecurityConfig.passwordEncoder().encode(userData.getPassword()));
        user.setTelNumber(userData.getTelNumber());
        user.setEmail(userData.getEmail());
        user.setUsername(userData.getUsername());
        user.setGroup(groupRepository.findById(1).get());  //set default value

        Role userRole = roleRepository.findByName("ROLE_USER");
        user.setRoles(Arrays.asList(userRole));

        user = userRepository.save(user);



        return "redirect:/login";
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
