package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.models.Room;
import org.springframework.hateoas.ResourceSupport;

public class RoomResource extends ResourceSupport {

    private final Room room;

    public RoomResource(Room room) {
        this.room = room;
    }

    public Room getRoom() {
        return room;
    }
}
