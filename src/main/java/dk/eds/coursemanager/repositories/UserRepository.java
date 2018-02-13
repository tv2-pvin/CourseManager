package dk.eds.coursemanager.repositories;

import dk.eds.coursemanager.models.User;
import dk.eds.coursemanager.models.UserType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    boolean existsByUsername(String username);

    User getUserByUsername(String username);

    List<User> getUsersByUserType(UserType userType);
}
