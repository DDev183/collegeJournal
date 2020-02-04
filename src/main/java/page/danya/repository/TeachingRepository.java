package page.danya.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import page.danya.models.APP_User;
import page.danya.models.Group;
import page.danya.models.Subject;
import page.danya.models.Teaching;

import java.util.List;

@Repository
public interface TeachingRepository extends JpaRepository<Teaching, Integer> {

    List<Teaching> findByTeacher(APP_User teacher);


}
