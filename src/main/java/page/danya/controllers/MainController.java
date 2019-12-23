package page.danya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import page.danya.models.APP_User;
import page.danya.repository.APP_UserRepository;

import java.util.Optional;

@Controller
public class MainController {

    @Autowired
    private APP_UserRepository APPUserRepository;


    @GetMapping("/")
    public String greeting(Model model) {
        return "hello";
    }


    @GetMapping("/test")
    public String test(@RequestParam(name="lastname") String lastname, Model model){
        model.addAttribute("lastname", lastname);  //first- name of url, second- value to output
        return "test";
    }

//    @GetMapping("/createUser")
//    public String getViewCreateUser(Model model){
//        return "createuser";
//    }
//
//    @PostMapping("/createUser")
//    public String createUser(@RequestParam String firstName, @RequestParam String lastName, Model model){
//
//        APP_User user = new APP_User(firstName, lastName);
//        user.setFirstName(firstName);
//        user.setLastName(lastName);
//
//        user = APPUserRepository.save(user);
//
//        model.addAttribute("firstName", user.getFirstName());
//        model.addAttribute("lastName", user.getLastName());
//
//
//        return "createuser";
//    }


    @PostMapping("/findByLastName")
    public String findByLastName(@RequestParam String lastName, Model model){

        Optional<APP_User> user = APPUserRepository.findByLastname(lastName);

        if (user.isPresent()) {
            model.addAttribute("firstName", user.get().getFirstname());
            model.addAttribute("lastName", user.get().getLastname());
        }

        return "findByLastName";
    }


    @GetMapping("/findByLastName")
    public String findByLastName(Model model){

        return "findByLastName";
    }





    @PostMapping("/deleteById")
    public String deleteById(@RequestParam int id, Model model){

        model.addAttribute("id", id);
        model.addAttribute("lastName", APPUserRepository.findById(id).get().getLastname());

        APPUserRepository.deleteById(id);

        return "delete";
    }

    @GetMapping("/listOfStudents")
    public Iterable<APP_User> getAllStudents(){
        return APPUserRepository.findAll();


    }

    @PostMapping("/updateLastName")
    public String updateLastName(@RequestParam String oldLastName, @RequestParam String newLastName, Model model){

        Optional<APP_User> user = APPUserRepository.findByLastname(oldLastName);

        user.get().setLastname(newLastName);

        APPUserRepository.save(user.get());

        model.addAttribute("oldLastName", oldLastName);
        model.addAttribute("newLastName", newLastName);


        return "updateLastName";
    }


//    @GetMapping("/getInfoAboutStudent")
//    public String getInfo(@RequestParam int id, Model model){
//
//        Optional<Student> student = studentRepository.findById(id);
//
//        if (student.isPresent()){
//            model.addAttribute("id", id);
//            model.addAttribute("firstName", student.get().getFirstName());
//            model.addAttribute("lastName", student.get().getLastName());
//
//        }
//
//        return "info";
//    }

    @GetMapping("/admin")
    public String getAdminPage(Model model){
        return "admin";
    }


}