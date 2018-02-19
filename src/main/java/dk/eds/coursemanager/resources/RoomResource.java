package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.controllers.RoomController;
import dk.eds.coursemanager.models.Room;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class RoomResource extends ResourceSupportBase {

    private final Room room;


    public RoomResource(Room room) {
        this.room = room;
        this.add(
                linkTo(methodOn(RoomController.class).getRoom(room.getId())).withSelfRel(),
                linkTo(methodOn(RoomController.class).getAllRoomBookings(room.getId())).withRel("bookings")
        );
    }

    public Room getRoom() {
        return room;
    }
}
