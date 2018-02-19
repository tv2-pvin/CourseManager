package dk.eds.coursemanager.controllers.helpers;

import dk.eds.coursemanager.models.City;
import dk.eds.coursemanager.models.Location;
import dk.eds.coursemanager.models.Person;
import dk.eds.coursemanager.repositories.CityRepository;
import dk.eds.coursemanager.repositories.LocationRepository;

public class ControllerUtils {

    public static Location createOrFetchLocation(Location loc, LocationRepository locationRepository, CityRepository cityRepository) {
        createOrFetchCity(loc, cityRepository);
        if (locationRepository.existsLocationByRoadNameAndRoadNumberAndRoadNumberDetailAndCity_ZipCode(loc.getRoadName(), loc.getRoadNumber(), loc.getRoadNumberDetail(), loc.getCity().getZipCode()))
            return locationRepository.getLocationByRoadNameAndRoadNumberAndRoadNumberDetailAndCity_ZipCode(loc.getRoadName(), loc.getRoadNumber(), loc.getRoadNumberDetail(), loc.getCity().getZipCode());
        else
            return locationRepository.save(loc);
    }

    private static City createOrFetchCity(Location location, CityRepository cityRepository) {
        City userCity = location.getCity();
        if (!cityRepository.existsCityByZipCode(userCity.getZipCode()))
            return cityRepository.save(userCity);
        else
            return cityRepository.getCityByZipCode(userCity.getZipCode());
    }
}
