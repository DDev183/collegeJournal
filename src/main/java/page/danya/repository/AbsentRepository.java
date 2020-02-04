package page.danya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import page.danya.models.Absent;

import java.util.Optional;

@Repository
public interface AbsentRepository extends JpaRepository<Absent, Integer> {

    Optional<Absent> findByShortname(String shortname);
    Optional<Absent> findByName(String name);

}
