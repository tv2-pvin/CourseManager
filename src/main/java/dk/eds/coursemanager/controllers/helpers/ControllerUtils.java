package dk.eds.coursemanager.controllers.helpers;

import dk.eds.coursemanager.models.City;
import dk.eds.coursemanager.models.Location;
import dk.eds.coursemanager.models.Person;
import dk.eds.coursemanager.repositories.CityRepository;
import dk.eds.coursemanager.repositories.LocationRepository;

public class ControllerUtils {

    public static Location createOrFetchLocation(Person person, LocationRepository locationRepository) {
        Location loc = person.getLocation();
        if (locationRepository.existsLocationByRoadNameAndRoadNumberAndRoadNumberDetailAndCity_ZipCode(loc.getRoadName(), loc.getRoadNumber(), loc.getRoadNumberDetail(), loc.getCity().getZipCode()))
            return locationRepository.getLocationByRoadNameAndRoadNumberAndRoadNumberDetailAndCity_ZipCode(loc.getRoadName(), loc.getRoadNumber(), loc.getRoadNumberDetail(), loc.getCity().getZipCode());
        else
            return locationRepository.save(loc);
    }

    public static City createOrFetchCity(Person person, CityRepository cityRepository) {
        City userCity = person.getLocation().getCity();
        if (!cityRepository.existsCityByZipCode(userCity.getZipCode()))
            return cityRepository.save(userCity);
        else
            return cityRepository.getCityByZipCode(userCity.getZipCode());
    }
}
