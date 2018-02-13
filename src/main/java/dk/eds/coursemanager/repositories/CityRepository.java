package dk.eds.coursemanager.repositories;

import dk.eds.coursemanager.models.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    boolean existsCityByZipCode(int zipCode);

    City getCityByZipCode(int zipCode);
}
