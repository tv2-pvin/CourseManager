package dk.eds.coursemanager.repositories;

import dk.eds.coursemanager.models.Price;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PriceRepository extends JpaRepository<Price, Long> {

    List<Price> getAllByCourse_Id(Long id);

    List<Price> getAllByParticipantType_Id(Long id);
}
