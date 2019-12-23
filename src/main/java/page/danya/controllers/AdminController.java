package page.danya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.thymeleaf.spring5.view.ThymeleafViewResolver;
import page.danya.models.APP_User;
import page.danya.models.Group;
import page.danya.repository.APP_UserRepository;
import page.danya.repository.GroupRepository;

import java.util.List;
import java.util.Optional;

@Controller
public class AdminController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private APP_UserRepository userRepository;

    @GetMapping("/admin/createGroup")
    public String getCreateGroup(Model model){

        model.addAttribute("createGroupForm", new Group());
        return "admin/createGroup";
    }


    @PostMapping("/admin/createGroup")
    public String addGroup(@ModelAttribute(name = "createGroupForm") Group groupInfo, Model model){

        Group group = new Group();
        group.setName(groupInfo.getName());

        group = groupRepository.save(group);

        return "admin";
    }


    @GetMapping("/admin/addStudentToGroup")
    public String getAddStudentToGroup(String lastname, Model model){

        model.addAttribute("firstname", lastname);

        return "admin/addStudentToGroup";

    }

    @PostMapping("/admin/addStudentToGroup")
    public String findAddStudentToGroup(@ModelAttribute(name = "findStudent") String lastname, Model model){

//        Optional<APP_User> user = userRepository.findByUsername(lastname);
//        APP_User userInfo = user.get();
//
//        List<Group> groups = groupRepository.findAll();
//        model.addAttribute("firstname", userInfo.getFirstname());
//        model.addAttribute("lastname", userInfo.getLastname());
//        model.addAttribute("middlename", userInfo.getMiddlename());
//
//        model.addAttribute("groups", groups);



        return "admin/addStudentToGroup";
    }



//    @PostMapping("/admin/addStudentToGroup")
//    public String AddStudentToGroup(@ModelAttribute(name = "addStudentToGroup") String lastname, Model model){
//
//
//        return "/admin";
//    }




}
