package page.danya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import page.danya.models.Group;

import java.util.Optional;

public interface GroupRepository extends JpaRepository<Group, Integer> {


}
