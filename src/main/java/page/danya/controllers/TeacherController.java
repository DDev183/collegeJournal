package page.danya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import page.danya.DAO.createAbsentDAO;
import page.danya.models.*;
import page.danya.repository.*;

import javax.swing.text.DateFormatter;
import java.security.Principal;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Controller
public class TeacherController {

    @Autowired
    private APP_UserRepository userRepository;

    @Autowired
    private TeachingRepository teachingRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private AbsentRepository absentRepository;

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

    @GetMapping("/teacher/createLessonChoose")
    private String getCreateLessonPage(Model model, Principal principal){

        APP_User user = userRepository.findByUsername(principal.getName()).get();
        List<Teaching> teachingList = teachingRepository.findByTeacher(user);


        Set<Group> groups = new HashSet<>();
        Set<Subject> subjects = new HashSet<>();
        
        for (int i = 0; i < teachingList.size(); i++){
            groups.add(teachingList.get(i).getGroup());
            subjects.add(teachingList.get(i).getSubject());
        }

        DateTimeFormatter d = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = LocalDate.now().format(d);

        model.addAttribute("date", date);
        model.addAttribute("groups", groups);
        model.addAttribute("subjects", subjects);
        
        
        

        System.out.println("++++++");
//        System.out.println(groups.size());
        System.out.println(subjects.size());

        return "/teacher/createLessonChoose";
    }


    @PostMapping("/teacher/createLesson")
    public String postCreateLessonChoose(Model model, Principal principal, @RequestParam(name = "goodGroup") String goodGroup, @RequestParam(name = "goodSubject") String goodSubject){


        //Select info from database
        APP_User user = userRepository.findByUsername(principal.getName()).get();
        Group group = groupRepository.findByName(goodGroup).get();
        Subject subject = subjectRepository.findByName(goodSubject).get();
        List<APP_User> students = userRepository.findByGroup(group);

        //Create list with absent
        List<Absent> absents = new ArrayList<>();
        absents.add(absentRepository.findByName("Присутствует").get());     // be careful!
        absents.add(absentRepository.findByShortname("НБ").get());

        //Take current date
        DateTimeFormatter d = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        String date = LocalDate.now().format(d);

        //DAO for form in frontend
        createAbsentDAO absentDAO = new createAbsentDAO(students.size());
        for (int i = 0; i < students.size(); i++) {
            APP_User student = students.get(i);
            absentDAO.addObjectToList(new AbsentWithStudent(student.getId(), student.getFirstname(), student.getLastname(), absents, date));
        }

        //Sent DAO to frontend
        model.addAttribute("absentDAO", absentDAO);

//        model.addAttribute("absentList", absents);


        return "/teacher/createLesson";
    }

    @PostMapping("/teacher/createLesson/confirm")
    public String confirmCreateLesson(Model model, @ModelAttribute(name = "absentDAO") createAbsentDAO absentDAO){



        System.out.println("DAO count: " + absentDAO.getList().size());


        return "/teacher/main";
    }

}
