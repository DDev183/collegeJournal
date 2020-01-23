package page.danya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import page.danya.models.APP_User;
import page.danya.models.Role;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);


}
