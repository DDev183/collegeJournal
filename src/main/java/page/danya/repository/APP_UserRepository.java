package page.danya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import page.danya.models.APP_User;

import java.util.Optional;

public interface APP_UserRepository extends JpaRepository<APP_User, Integer> {

    Optional<APP_User> findByLastname(String lastName);

    Optional<APP_User> findByLastname(int id);

    Optional<APP_User> findByUsername(String username);

    Optional<APP_User> findByFirstnameAndLastname(String firstname, String lastname);






}
