package page.danya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
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
    public String getAddStudentToGroup(String lastname, String firstname, Model model){

        model.addAttribute("lastname", lastname);
        model.addAttribute("firstname", firstname);

        return "admin/addStudentToGroup";

    }

    @PostMapping("/admin/addStudentToGroup")
    public String findAddStudentToGroup(@RequestParam String firstname, @RequestParam String lastname, Model model){


        Optional<APP_User> user = userRepository.findByFirstnameAndLastname(firstname, lastname);
        APP_User userInfo = user.get();

        System.out.println(userInfo.toString());
        System.out.println(userInfo.getGroup().getName());


        List<Group> groups = groupRepository.findAll();

        model.addAttribute("user", userInfo);
        model.addAttribute("id", userInfo.getId());
        model.addAttribute("firstname", userInfo.getFirstname());
        model.addAttribute("lastname", userInfo.getLastname());
        model.addAttribute("middlename", userInfo.getMiddlename());

        model.addAttribute("groups", groups);



        return "admin/addStudentToGroup";
    }



    @PostMapping("/admin/addStudentToGroup/link")
    public String addStudentToGroup(@RequestParam(name = "groupid") int groupid, @RequestParam(name = "id") int id, Model model){


        System.out.println("User id: " + id);
        System.out.println("Group id: " + groupid);
        System.out.println("EKEKEKEKEKEKEKEKE");

        Group group = groupRepository.findById(groupid).get();

        APP_User user = userRepository.findById(id).get();
        user.setGroup(group);

        userRepository.save(user);

        return "redirect:/admin";
    }

    @GetMapping("/admin/createSubject")
    public String createSubject(Model model){

        return "/admin/createSubject";
    }




}
