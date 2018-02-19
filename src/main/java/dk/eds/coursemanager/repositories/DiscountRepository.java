package dk.eds.coursemanager.repositories;

import dk.eds.coursemanager.models.Discount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiscountRepository extends JpaRepository<Discount, Long> {

    List<Discount> getDiscountsByCourseType_Id(Long id);

    List<Discount> getDiscountsByParticipantType_Id(Long id);

    List<Discount> getDiscountsByCourseType_IdAndParticipantType_Id(Long courseTypeId, Long participantTypeId);
}
