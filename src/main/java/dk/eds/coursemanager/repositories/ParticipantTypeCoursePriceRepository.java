package dk.eds.coursemanager.repositories;

import dk.eds.coursemanager.models.ParticipantTypeCoursePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantTypeCoursePriceRepository extends JpaRepository<ParticipantTypeCoursePrice, Long> {
}
