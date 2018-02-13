package dk.eds.coursemanager.controllers.helpers;

import dk.eds.coursemanager.models.City;
import dk.eds.coursemanager.models.Location;
import dk.eds.coursemanager.models.User;
import dk.eds.coursemanager.repositories.CityRepository;
import dk.eds.coursemanager.repositories.LocationRepository;

public class ControllerUtils {

    public static Location createOrFetchLocation(User user, LocationRepository locationRepository) {
        Location loc = user.getLocation();
        if (locationRepository.existsLocationByRoadNameAndRoadNumberAndRoadNumberDetailAndCity(loc.getRoadName(), loc.getRoadNumber(), loc.getRoadNumberDetail(), loc.getCity()))
            return locationRepository.getLocationByRoadNameAndRoadNumberAndRoadNumberDetailAndCity(loc.getRoadName(), loc.getRoadNumber(), loc.getRoadNumberDetail(), loc.getCity());
        else
            return locationRepository.save(loc);
    }

    public static City createOrFetchCity(User user, CityRepository cityRepository) {
        City userCity = user.getLocation().getCity();
        if (!cityRepository.existsCityByZipCode(userCity.getZipCode()))
            return cityRepository.save(userCity);
        else
            return cityRepository.getCityByZipCode(userCity.getZipCode());
    }
}
