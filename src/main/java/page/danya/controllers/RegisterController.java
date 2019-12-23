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
import page.danya.service.UserService;

import java.util.Collections;

@Controller
public class RegisterController {

    @Autowired
    private APP_UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private WebSecurityConfig webSecurityConfig;

    @GetMapping("/registration")
    public String register(Model model){
        System.out.println("Test");
        model.addAttribute("registerForm", new APP_User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute(name = "registerForm") APP_User userData, Model model){



//        System.out.println("POSTMAPPING");
//        System.out.println(userData.getFirstname() + "\n" + userData.getLastname() + "\n" + userData.getUsername() + "\n" + userData.getPassword() + "\n" + userData.getEmail() + "\n" + userData.getTelNumber());

//        if (userRepository.findByUsername(userData.getUsername()).get() != null){
//            model.addAttribute("message", "User already created!");
//            return "registration";
//        }

        APP_User user = new APP_User();

        user.setFirstname(userData.getFirstname());
        user.setLastname(userData.getLastname());
        user.setPassword(webSecurityConfig.passwordEncoder().encode(userData.getPassword()));
        user.setTelNumber(userData.getTelNumber());
        user.setEmail(userData.getEmail());
        user.setUsername(userData.getUsername());
        user.setGroup(groupRepository.findById(10).get());  //set default value

        user.setRoles(Collections.singleton(Role.USER));

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
