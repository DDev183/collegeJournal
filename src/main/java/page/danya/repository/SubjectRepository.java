package page.danya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import page.danya.models.Subject;
import page.danya.models.Teaching;

import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

    Optional<Subject> findByName(String name);

}
