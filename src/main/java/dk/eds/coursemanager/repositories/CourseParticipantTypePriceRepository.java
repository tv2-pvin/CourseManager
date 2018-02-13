package dk.eds.coursemanager.repositories;

import dk.eds.coursemanager.models.CourseParticipantTypePrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CourseParticipantTypePriceRepository extends JpaRepository<CourseParticipantTypePrice, Long> {
}
