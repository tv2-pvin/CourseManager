package dk.eds.coursemanager.repositories;

import dk.eds.coursemanager.models.Participant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseParticipantRepository extends JpaRepository<Participant, Long> {

    List<Participant> findAllByCourse_Id(Long id);

    List<Participant> findAllByParticipantType_Id(Long id);

    List<Participant> findAllByParticipant_User_Username(String username);
}
