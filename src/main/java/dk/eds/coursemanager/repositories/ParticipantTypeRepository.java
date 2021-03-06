package dk.eds.coursemanager.repositories;

import dk.eds.coursemanager.models.ParticipantType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantTypeRepository extends JpaRepository<ParticipantType, Long> {
}
