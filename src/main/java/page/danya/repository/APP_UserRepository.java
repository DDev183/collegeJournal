package page.danya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import page.danya.models.APP_User;
import page.danya.models.Role;

import java.util.List;
import java.util.Optional;

public interface APP_UserRepository extends JpaRepository<APP_User, Integer> {

    Optional<APP_User> findByLastname(String lastName);

    Optional<APP_User> findByLastname(int id);

    Optional<APP_User> findByUsername(String username);

    Optional<APP_User> findByFirstnameAndLastname(String firstname, String lastname);

    Optional<APP_User> findByFirstnameAndLastnameAndMiddlename(String firstname, String lastname, String middlename);

    List<APP_User> findByRole(Role role);




}
