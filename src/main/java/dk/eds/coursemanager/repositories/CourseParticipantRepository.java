package dk.eds.coursemanager.repositories;

import dk.eds.coursemanager.models.CourseParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseParticipantRepository extends JpaRepository<CourseParticipant, Long> {
}
