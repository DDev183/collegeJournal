package page.danya.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import page.danya.models.APP_User;
import page.danya.models.Teaching;

import java.util.List;

public interface TeachingRepository extends JpaRepository<Teaching, Integer> {

    List<Teaching> findByTeacher(APP_User teacher);

}
