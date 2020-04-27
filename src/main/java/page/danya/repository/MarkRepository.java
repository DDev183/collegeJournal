package page.danya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import page.danya.models.APP_User;
import page.danya.models.Lesson;
import page.danya.models.Mark;
import page.danya.models.Teaching;

import java.util.Date;
import java.util.Optional;

public interface MarkRepository extends JpaRepository<Mark, Integer> {

    Optional<Mark> findByLessonAndStudent(Lesson lesson, APP_User student);



}
