package page.danya.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import page.danya.models.APP_User;
import page.danya.repository.APP_UserRepository;

import java.util.Optional;


@Service
public class UserService implements UserDetailsService {

    @Autowired
    private APP_UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<APP_User> user = userRepository.findByUsername(username);

        return user.get();
    }
}
