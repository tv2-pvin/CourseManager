package dk.eds.coursemanager.repositories;

import dk.eds.coursemanager.models.City;
import dk.eds.coursemanager.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocationRepository extends JpaRepository<Location, Long> {

    boolean existsLocationByRoadNameAndRoadNumberAndRoadNumberDetailAndCity_ZipCode(String roadName, int roadNumber, String roadNumberDetail, int zipCode);

    Location getLocationByRoadNameAndRoadNumberAndRoadNumberDetailAndCity_ZipCode(String roadName, int roadNumber, String roadNumberDetail, int zipCode);

    List<Location> getLocationsByCity_ZipCode(int zipCode);
}
