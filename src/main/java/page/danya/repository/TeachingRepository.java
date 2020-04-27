package page.danya.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import page.danya.models.APP_User;
import page.danya.models.Group;
import page.danya.models.Subject;
import page.danya.models.Teaching;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeachingRepository extends JpaRepository<Teaching, Integer> {

    List<Teaching> findByTeacher(APP_User teacher);


    //Pls refactor this from list to optional type
    List<Teaching> findByTeacherAndGroup(APP_User teacher, Group group);

    Optional<Teaching> findByTeacherAndGroupAndSubject(APP_User teacher, Group group, Subject subject);


}
