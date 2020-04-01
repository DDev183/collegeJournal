package page.danya.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import page.danya.models.APP_User;
import page.danya.repository.APP_UserRepository;
import page.danya.security.jwt.JwtUser;
import page.danya.security.jwt.JwtUserFactory;

import java.util.Optional;


@Service
@Slf4j
public class UserService implements UserDetailsService {

    @Autowired
    private APP_UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        return userRepository.findByUsername(username).get();
        APP_User user = userRepository.findByUsername(username).get();

        if (user == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        JwtUser jwtUser = JwtUserFactory.create(user);
        System.out.println("IN loadUserByUsername - user with username: {} successfully loaded " + username);
        return jwtUser;
    }
}
