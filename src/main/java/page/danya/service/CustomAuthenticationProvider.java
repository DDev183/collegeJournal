//package page.danya.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.authentication.BadCredentialsException;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Component;
//import page.danya.models.APP_User;
//import page.danya.repository.APP_UserRepository;
//import page.danya.security.UserService;
//
//import java.util.Collections;
//
//@Component
//public class CustomAuthenticationProvider implements AuthenticationProvider{
//
//    @Autowired
//    private UserService userService;
//
//    @Autowired
//    private APP_UserRepository userRepository;
//
//
//
//
//    //Custom provider
//
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//
//        String username = authentication.getName();
//        String password = authentication.getCredentials().toString();
//
//
//        APP_User user = userRepository.findByUsername(username).get();
//        String dbUsername = user.getUsername();
//        String bdPassword = user.getPassword();
//
////        userService.passwordEncoder();
//
//
//        if (dbUsername.equals(username) && bdPassword.equals(password)) {
//            return new UsernamePasswordAuthenticationToken
//                    (username, password, Collections.emptyList());
//        } else {
//            throw new
//                    BadCredentialsException("External system authentication failed");
//        }
//    }
//
//    @Override
//    public boolean supports(Class<?> authentication) {
//        return false;
//    }
//
//
////    @Override
////    public boolean supports(Class<?> auth) {
////        return auth.equals(UsernamePasswordAuthenticationToken.class);
////    }
//
//
//
//}