package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.models.Location;
import org.springframework.hateoas.ResourceSupport;

public class LocationResource extends ResourceSupportBase {

    private final Location location;

    public LocationResource(Location location) {
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
