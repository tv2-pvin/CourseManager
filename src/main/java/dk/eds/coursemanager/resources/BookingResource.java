package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.models.Booking;
import org.springframework.hateoas.ResourceSupport;

public class BookingResource extends ResourceSupport {

    private final Booking booking;

    public BookingResource(Booking booking) {
        this.booking = booking;
    }

    public Booking getBooking() {
        return booking;
    }
}
