package page.danya.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import page.danya.models.TokenStorage;

import java.util.Optional;


public interface TokenRepository extends JpaRepository<TokenStorage, Long> {

    Optional<TokenStorage> findByUsername(String username);

    Optional<TokenStorage> findByToken(String token);

    void deleteByUsername(String username);

}
