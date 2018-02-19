package dk.eds.coursemanager.repositories;

import dk.eds.coursemanager.models.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> getAllByRoom_Id(Long id);

    List<Booking> getAllByCourse_Id(Long id);

    List<Booking> getAllByBooker_User_Username(String username);

    List<Booking> getAllByFromTimeAfterAndToTimeBefore(Date fromTime, Date toTime);
}
