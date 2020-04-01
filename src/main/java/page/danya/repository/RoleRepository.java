package page.danya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import page.danya.models.APP_User;
import page.danya.models.Role;

import java.util.List;
import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    List<Role> findByName(String name);


//    Optional<Role> findByName(String name);



}
