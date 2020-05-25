package page.danya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import page.danya.models.APP_User;
import page.danya.models.EnglishDependent;
import page.danya.models.Group;
import page.danya.models.Subject;

import java.util.List;
import java.util.Optional;

@Repository
public interface EnglishRepository extends JpaRepository<EnglishDependent, Integer> {

    Optional<EnglishDependent> findByGroupAndSubjectAndTeacher(Group group, Subject subject, APP_User teacher);


}
