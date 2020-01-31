package page.danya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import page.danya.models.APP_User;
import page.danya.models.Teaching;
import page.danya.repository.APP_UserRepository;
import page.danya.repository.TeachingRepository;

import java.security.Principal;
import java.util.List;

@Controller
public class TeacherController {

    @Autowired
    private APP_UserRepository userRepository;

    @Autowired
    private TeachingRepository teachingRepository;

    @GetMapping("/teacher")
    public String getTeacherPage(Model model){
        return "/teacher/main";
        //TEST
    }

    @GetMapping("/teacher/showLoad")
    public String getShowLoadPage(Model model, Principal principal){

        APP_User user = userRepository.findByUsername(principal.getName()).get();

        List<Teaching> teachingList = teachingRepository.findByTeacher(user);

        model.addAttribute("teachingList", teachingList);

        model.addAttribute("lastname", user.getLastname());


        return "/teacher/showLoad";
    }

}
