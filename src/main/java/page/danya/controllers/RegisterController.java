package page.danya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import page.danya.models.APP_User;
import page.danya.models.Role;
import page.danya.repository.APP_UserRepository;

import java.util.Collections;

@Controller
public class RegisterController {

    @Autowired
    APP_UserRepository userRepository;

    @GetMapping("/registration")
    public String register(Model model){
        System.out.println("kek");
        model.addAttribute("registerForm", new APP_User());
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@ModelAttribute(name = "registerForm") APP_User userData, Model model){



//        System.out.println("POSTMAPPING");
        System.out.println(userData.getFirstname() + "\n" + userData.getLastname() + "\n" + userData.getUsername() + "\n" + userData.getPassword() + "\n" + userData.getEmail() + "\n" + userData.getTelNumber());

//        if (userRepository.findByUsername(userData.getUsername()).get() != null){
//            model.addAttribute("message", "User already created!");
//            return "registration";
//        }

        APP_User user = new APP_User();

        user.setFirstname(userData.getFirstname());
        user.setLastname(userData.getLastname());
        user.setPassword(userData.getPassword());
        user.setTelNumber(userData.getTelNumber());
        user.setEmail(userData.getEmail());
        user.setUsername(userData.getUsername());

        user.setRoles(Collections.singleton(Role.USER));

        user = userRepository.save(user);

        return "redirect:/login";
    }

}
