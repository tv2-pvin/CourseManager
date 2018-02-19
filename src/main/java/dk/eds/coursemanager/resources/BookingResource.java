package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.controllers.BookingController;
import dk.eds.coursemanager.controllers.CourseController;
import dk.eds.coursemanager.controllers.PersonController;
import dk.eds.coursemanager.controllers.RoomController;
import dk.eds.coursemanager.models.Booking;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class BookingResource extends ResourceSupportBase {

    private final Booking booking;

    public BookingResource(Booking booking) {
        this.booking = booking;
        this.add(
                linkTo(methodOn(BookingController.class).getBooking(booking.getId())).withSelfRel(),
                linkTo(methodOn(RoomController.class).getRoom(booking.getRoom().getId())).withRel("room"),
                linkTo(methodOn(CourseController.class).getCourse(booking.getCourse().getCourseType().getId(), booking.getCourse().getId())).withRel("course"),
                linkTo(methodOn(PersonController.class).getPerson(booking.getBooker().getPersonType().getId(), booking.getBooker().getUser().getUsername())).withRel("person")
        );
    }

    public Booking getBooking() {
        return booking;
    }
}
