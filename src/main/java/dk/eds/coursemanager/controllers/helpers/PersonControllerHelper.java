package dk.eds.coursemanager.controllers.helpers;

import dk.eds.coursemanager.models.City;
import dk.eds.coursemanager.models.Location;
import dk.eds.coursemanager.models.Person;
import dk.eds.coursemanager.repositories.CityRepository;
import dk.eds.coursemanager.repositories.LocationRepository;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;

public class PersonControllerHelper {

    public static void setCity(@Valid @RequestBody Person person, CityRepository cityRepository) {
        City userCity = person.getLocation().getCity();
        if (!cityRepository.existsCityByZipCode(userCity.getZipCode()))
            userCity = cityRepository.save(userCity);
        else
            userCity = cityRepository.getCityByZipCode(userCity.getZipCode());
        person.getLocation().setCity(userCity);
    }

    public static void setLocation(@Valid @RequestBody Person person, LocationRepository locationRepository) {
        Location loc = person.getLocation();
        if (locationRepository.existsLocationByRoadNameAndRoadNumberAndRoadNumberDetailAndCity_ZipCode(loc.getRoadName(), loc.getRoadNumber(), loc.getRoadNumberDetail(), loc.getCity().getZipCode()))
            loc = locationRepository.getLocationByRoadNameAndRoadNumberAndRoadNumberDetailAndCity_ZipCode(loc.getRoadName(), loc.getRoadNumber(), loc.getRoadNumberDetail(), loc.getCity().getZipCode());
        else
            loc = locationRepository.save(loc);
        person.setLocation(loc);
    }
}
