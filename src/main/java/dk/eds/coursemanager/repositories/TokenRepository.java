package dk.eds.coursemanager.repositories;

import dk.eds.coursemanager.models.Token;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TokenRepository extends JpaRepository<Token, Long> {

    Token getByTokenAndIsDeletedFalse(String token);
}
