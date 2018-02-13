package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.models.City;
import org.springframework.hateoas.ResourceSupport;

public class CityResource extends ResourceSupport {

    private final City city;

    public CityResource(City city) {
        this.city = city;
    }

    public City getCity() {
        return city;
    }
}
