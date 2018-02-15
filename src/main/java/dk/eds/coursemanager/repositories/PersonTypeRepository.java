package dk.eds.coursemanager.repositories;

import dk.eds.coursemanager.models.PersonType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonTypeRepository extends JpaRepository<PersonType, Long> {


}
