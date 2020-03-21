package page.danya.security;

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
public class UserService implements UserDetailsService {

    @Autowired
    private APP_UserRepository userRepository;




    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<APP_User> user = userRepository.findByUsername(username);





        if (user.isPresent()){
            JwtUser jwtUser = JwtUserFactory.create(user.get());
            return jwtUser;
        } else {
            throw new UsernameNotFoundException("User not found");
        }

    }


}
