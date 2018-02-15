package dk.eds.coursemanager.repositories;

import dk.eds.coursemanager.models.Person;
import dk.eds.coursemanager.models.User;
import dk.eds.coursemanager.models.PersonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PersonRepository extends JpaRepository<Person, Long> {
    Person findPersonByUser(User user);

    List<Person> findPeopleByPersonType(PersonType personType);
}
