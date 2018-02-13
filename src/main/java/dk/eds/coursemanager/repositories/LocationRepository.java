package dk.eds.coursemanager.repositories;

import dk.eds.coursemanager.models.City;
import dk.eds.coursemanager.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    boolean existsLocationByRoadNameAndRoadNumberAndRoadNumberDetailAndCity(String roadName, int roadNumber, String roadNumberDetail, City city);

    Location getLocationByRoadNameAndRoadNumberAndRoadNumberDetailAndCity(String roadName, int roadNumber, String roadNumberDetail, City city);
}
