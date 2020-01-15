package page.danya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import page.danya.models.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {

    Role findByName(String name);

}
