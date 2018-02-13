package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.models.Location;
import org.springframework.hateoas.ResourceSupport;

public class LocationResource extends ResourceSupport {

    private final Location location;

    public LocationResource(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
