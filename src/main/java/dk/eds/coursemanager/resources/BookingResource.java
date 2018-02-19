package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.models.Booking;

public class BookingResource extends ResourceSupportBase {

    private final Booking booking;

    public BookingResource(Booking booking) {
        this.booking = booking;
    }

    public Booking getBooking() {
        return booking;
    }
}
