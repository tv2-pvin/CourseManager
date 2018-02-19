package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.models.Room;
import org.springframework.hateoas.ResourceSupport;

public class RoomResource extends ResourceSupportBase {

    private final Room room;

    public RoomResource(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }
}
