package page.danya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import page.danya.models.Lesson;
import page.danya.models.Teaching;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface LessonRepository extends JpaRepository<Lesson, Integer> {

    List<Lesson> findByTeaching(Teaching teaching);




    Optional<Lesson> findByTeachingAndDate(Teaching teaching, LocalDate date);

}
