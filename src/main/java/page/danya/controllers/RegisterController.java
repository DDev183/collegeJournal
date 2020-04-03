package page.danya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import page.danya.DTO.AuthenticationRequestDto;
import page.danya.config.WebSecurityConfig;
import page.danya.models.APP_User;
import page.danya.models.Group;
import page.danya.DTO.Person;
import page.danya.models.Role;
import page.danya.repository.APP_UserRepository;
import page.danya.repository.GroupRepository;
import page.danya.repository.RoleRepository;
import page.danya.security.UserService;
import page.danya.security.jwt.JwtTokenProvider;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.*;

@RestController
@CrossOrigin    //VERY VERY IMPORTANT THINGS!!!!
@RequestMapping(value = "/api/auth")
public class RegisterController {


    public final static String ROLE_USER = "USER";
    public final static String ROLE_TEACHER = "TEACHER";
    public final static String ROLE_ADMIN = "ADMIN";

    @Autowired
    private APP_UserRepository userRepository;

    @Autowired
    private GroupRepository groupRepository;

    @Autowired
    private WebSecurityConfig webSecurityConfig;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    @PostMapping(value = "/login")
    public ResponseEntity login(@RequestBody AuthenticationRequestDto requestDto){
        try {
            String username = requestDto.getUsername();
            String password = requestDto.getPassword();
//            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));

            System.out.println("LOGIN USERNAME: " + username);


            APP_User user = userRepository.findByUsername(username).get();

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
            }

            if (jwtTokenProvider.passwordEncoder().matches(requestDto.getPassword(), user.getPassword())) {


                String token = jwtTokenProvider.createToken(username, user.getRole());


                System.out.println("User: " + username + " has been successfully logging");


                Map<Object, Object> response = new HashMap<>();
                response.put("username", username);
                response.put("token", token);

                return ResponseEntity.ok(response);
            }

            return ResponseEntity.status(401).build();

        }catch (AuthenticationException e){
            throw new BadCredentialsException("Invalid username or password");
        }

    }


    void rolesInit(){
        if (roleRepository.findAll().isEmpty()) {
            roleRepository.save(new Role(ROLE_USER));
            roleRepository.save(new Role(ROLE_TEACHER));
            roleRepository.save(new Role(ROLE_ADMIN));
        }

    }

    @CrossOrigin    //VERY VERY IMPORTANT THINGS!!!!
    @PostMapping(value = "/registration")
    public ResponseEntity addUser(@RequestBody Person person) throws URISyntaxException {




        if (groupRepository.findById(1).isPresent() == false){
            Group group = new Group();
            group.setId(1);
            group.setName("Default group");

            group = groupRepository.save(group);
        }



        if (userRepository.findByUsername(person.getUsername()).isPresent()){
            return ResponseEntity.status(401).build();
        }

            APP_User user = new APP_User();


            rolesInit();




            user.setFirstname(person.getFirstname());
            user.setLastname(person.getLastname());
            user.setMiddlename(person.getMiddlename());
            user.setUsername(person.getUsername());
            user.setEmail(person.getEmail());
            user.setPassword(jwtTokenProvider.passwordEncoder().encode(person.getPassword()));
            user.setTelnumber(person.getTelnumber());
            user.setGroup(groupRepository.findById(1).get());  //set default value

            List<Role> roles = roleRepository.findByName(ROLE_USER);
            user.setRole(roles);



            System.out.println("User= " + user.getUsername() + " had been registered"); //Log

            userRepository.save(user);

            return ResponseEntity.ok(jwtTokenProvider.createToken(user.getUsername(), user.getRole()));

//        return ResponseEntity.ok().build();
    }





//    @PostMapping("/login")
//    public String login(@ModelAttribute(name = "loginForm") UserService user, Model model){
//
//        webSecurityConfig.configure();
//
//        return "hello";
//    }


//    @GetMapping("/login")
//    public String getLoginPage(Model model){
//        return "login";
//    }

}
