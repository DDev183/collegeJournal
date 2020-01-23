package page.danya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
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

@Controller
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

    @GetMapping("/registration")
    public String register(Model model){
        System.out.println("Test");
        model.addAttribute("registerForm", new APP_User());
        return "registration";
    }



    void rolesInit(){

        Role userRole = roleRepository.findByName(ROLE_USER);
        if (userRole == null) {
            userRole = new Role(ROLE_USER);
            roleRepository.save(userRole);
        }

        Role teacherRole = roleRepository.findByName(ROLE_TEACHER);
        if (teacherRole == null) {
            teacherRole = new Role(ROLE_TEACHER);
            roleRepository.save(teacherRole);
        }

        Role adminRole = roleRepository.findByName(ROLE_ADMIN);
        if (adminRole == null) {
            adminRole = new Role(ROLE_ADMIN);
            roleRepository.save(adminRole);
        }

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


        rolesInit();

        user.setFirstname(userData.getFirstname());
        user.setLastname(userData.getLastname());
        user.setMiddlename(userData.getMiddlename());
        user.setPassword(webSecurityConfig.passwordEncoder().encode(userData.getPassword()));
        user.setTelnumber(userData.getTelnumber());
        user.setEmail(userData.getEmail());
        user.setUsername(userData.getUsername());
        user.setGroup(groupRepository.findById(1).get());  //set default value

        Role userRole = roleRepository.findByName(ROLE_USER);
        user.setRole(userRole);

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
