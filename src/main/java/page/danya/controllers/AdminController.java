package page.danya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import page.danya.config.WebSecurityConfig;
import page.danya.models.*;
import page.danya.repository.*;

import java.util.*;

@Controller
public class AdminController {

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private APP_UserRepository userRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private TeachingRepository teachingRepository;

    @Autowired
    private WebSecurityConfig webSecurityConfig;



    //CONSTANTs

    final static String BAN_TEXT = "Блокировка";
    final static String UNBAN_TEXT = "Разблокировка";




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

//        model.addAttribute("user", userInfo);
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


        model.addAttribute("createSubjectForm", new Subject());
        return "/admin/createSubject";
    }

    @PostMapping("/admin/createSubject")
    public String createSubjectForm(@ModelAttribute(name = "createSubjectForm") Subject subjectInfo, Model model){

        Subject subject = new Subject(subjectInfo.getName());

        subject = subjectRepository.save(subject);

        return "/admin";
    }



//    @GetMapping("/admin/subjectLinking")
//    public String getPageSubjectLinking(Model model){
//
//
//        return "/admin/subjectLinking";
//    }




    @GetMapping("/admin/changeRole")
    public String getChangeRole(String firstname, String lastname, String middlename, Model model){

        model.addAttribute("firstname", firstname);
        model.addAttribute("lastname", lastname);
        model.addAttribute("middlename", middlename);

        return "admin/changeRole";
    }

    @PostMapping("/admin/changeRole")
    public String changeRole(@RequestParam String firstname, @RequestParam String lastname, @RequestParam String middlename, Model model){


        APP_User user = userRepository.findByFirstnameAndLastnameAndMiddlename(firstname, lastname, middlename).get();

        model.addAttribute("id", user.getId());
        model.addAttribute("firstname", user.getFirstname());
        model.addAttribute("lastname", user.getLastname());
        model.addAttribute("middlename", user.getMiddlename());

        List<String> roles = new LinkedList<>();
        roles.add("USER");
        roles.add("TEACHER");
        roles.add("ADMIN");

        model.addAttribute("roles", roles);

        return "admin/changeRole";
    }


    @PostMapping("admin/changeRole/change")
    public String changingRole(@ModelAttribute(name = "rolevalue") String roles, @RequestParam(name = "id") int id, Model model){

        System.out.println("Role: " + roles);
        System.out.println("User id: " + id);

        APP_User user = userRepository.findById(id).get();


        System.out.println(user.toString());

//        user.setRoles(Collections.singleton(Role.USER));

        String roleString = "ROLE_" + roles;

        Role role = roleRepository.findByName(roleString);
        user.setRole(role);

        userRepository.save(user);

        System.out.println(user.toString());


        return "/admin";
    }


    @GetMapping("/admin/subjectLinking")
    public String getSubjectLinkingPage(String groupNumber, String subjectName, Model model){

        model.addAttribute("groupNumber", groupNumber);
        model.addAttribute("subjectName", subjectName);

        return "/admin/subjectLinking";
    }

    @PostMapping("/admin/subjectLinking")
    public String subjectLinkingPage(@RequestParam String groupNumber, String subjectName, Model model){

        Group group = groupRepository.findByName(groupNumber).get();

        List<Subject> subject = subjectRepository.findAll();   //Get all subjects from db
        List<Subject> goodSubject = new ArrayList<>();   //Create list for subject who store part of group name

        for(int i = 0; i != subject.size(); i++){
            if (subject.get(i).getName().contains(subjectName)){
                goodSubject.add(subject.get(i));
            }
        }

//        List<APP_User> goodTeachers = userRepository.findByRole(new Role(RegisterController.ROLE_TEACHER));
        List<APP_User> teachers = userRepository.findAll();
        List<APP_User> goodTeachers = new ArrayList<>();

        for(int i = 0; i != teachers.size(); i++){
            if (teachers.get(i).getRole().getName().equalsIgnoreCase(RegisterController.ROLE_TEACHER)){
                goodTeachers.add(teachers.get(i));
            }
        }


        model.addAttribute("groupNumber", group.getName());
        model.addAttribute("goodSubject", goodSubject);
        model.addAttribute("teachers", goodTeachers);



        return "/admin/subjectLinking";
    }


    @PostMapping("/admin/subjectLinking/link")
    public String subjectLinking(Model model, @RequestParam(name = "groupNumber") String groupNumber, @RequestParam String goodSubject, @RequestParam(name = "teachers") String goodTeachers){


        System.out.println("Group number: " + groupNumber + "\nSubject: " + goodSubject + "\nTeacher: " + goodTeachers);
        teachingRepository.save(new Teaching(groupRepository.findByName(groupNumber).get(), subjectRepository.findById(Integer.parseInt(goodSubject)).get(), userRepository.findById(Integer.parseInt(goodTeachers)).get()));


        return "/admin";
    }



    @GetMapping("/admin/banUser")
    public String getBanPage(Model model, String firstname, String lastname, String mediumname){

        model.addAttribute("firstname", firstname);
        model.addAttribute("lastname", lastname);
        model.addAttribute("mediumname", mediumname);

        return "/admin/banUser";
    }

    @PostMapping("/admin/banUser")
    public String banUser(Model model, String firstname, String lastname, String mediumname){


        APP_User user = userRepository.findByFirstnameAndLastnameAndMiddlename(firstname, lastname, mediumname).get();

        model.addAttribute("id", user.getId());
        model.addAttribute("firstname", firstname);
        model.addAttribute("lastname", lastname);
        model.addAttribute("mediumname", mediumname);


        String banstateF;

        if(user.isBanstate()){
            banstateF = BAN_TEXT;
        }else {
            banstateF = UNBAN_TEXT;
        }

        model.addAttribute("banstateF", banstateF);

        List<String> banstate = new ArrayList<String>();
        banstate.add(BAN_TEXT);
        banstate.add(UNBAN_TEXT);

        model.addAttribute("banstate", banstate);



        return "/admin/banUser";
    }

    @PostMapping("/admin/banUser/confirm")
    public String confirmBanState(Model model, @RequestParam(name = "id") int id, @RequestParam(name = "banstate") String banstate){

        APP_User user = userRepository.findById(id).get();

        System.out.println("Receive: " + banstate);

        if (banstate.equalsIgnoreCase(BAN_TEXT)){
            user.setBanstate(true);
        } else if (banstate.equalsIgnoreCase(UNBAN_TEXT)){
            user.setBanstate(false);
        } else {
            System.out.println("ERROR: Text from frontend did't response!");
        }

        System.out.println("Ban: " + user.isBanstate());
        user = userRepository.save(user);

        //TODO: Сделать реализацию проверки на блокировку при авторизации в системе!

        return "/admin";
    }

    @GetMapping("/admin/changerUserInfoByLogin")
    private String getChangerUserInfoByLoginPage(Model model){



        return "/admin/changerUserInfoByLogin";
    }

    @PostMapping("/admin/changerUserInfoMain")
    private String changerUserInfoByLoginPage(Model model, String username){

        APP_User user = userRepository.findByUsername(username).get();

        System.out.println(user.toString());

        model.addAttribute("user", user);

        return "/admin/changerUserInfoMain";
    }

    @PostMapping("/admin/changerUserInfoMain/confirm")
    private String changeUserInfoMyConfirm(Model model, @ModelAttribute(name = "changeUserInfo") APP_User user, @RequestParam(name = "id") int id){

        System.out.println(user.toString());

        APP_User currectUser = userRepository.findById(id).get();

        currectUser.setFirstname(user.getFirstname());
        currectUser.setLastname(user.getLastname());
        currectUser.setMiddlename(user.getMiddlename());
        currectUser.setUsername(user.getUsername());
        currectUser.setTelnumber(user.getTelnumber());
        currectUser.setEmail(user.getEmail());

        System.out.println(user.getPassword());
//        if (user.getPassword()){
//            currectUser.setPassword(webSecurityConfig.passwordEncoder().encode(user.getPassword()));
//        }

        userRepository.save(currectUser);

        return "/admin";
     }


}
