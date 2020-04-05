package page.danya.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import page.danya.DAO.ProfileDAO;
import page.danya.DTO.ProfileDTO;
import page.danya.models.APP_User;
import page.danya.repository.APP_UserRepository;
import page.danya.security.jwt.JwtTokenProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@CrossOrigin    //VERY VERY IMPORTANT THINGS!!!!
@RequestMapping(value = "/api/user")
public class MainController {


    @Autowired
    private APP_UserRepository userRepository;

    @Autowired
    JwtTokenProvider jwtTokenProvider;


    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping(value = "/profile", produces = "application/json")
    public ResponseEntity profile(@RequestHeader("Authorization") String token){
        token = token.substring(7, token.length());


        String username = jwtTokenProvider.getUsername(token);
        APP_User user = userRepository.findByUsername(username).get();


        ProfileDTO dto = new ProfileDTO();
        dto = dto.fromUser(user);


        return ResponseEntity.ok(dto);
    }

}