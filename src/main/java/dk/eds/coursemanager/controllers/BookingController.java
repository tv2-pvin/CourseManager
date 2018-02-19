package dk.eds.coursemanager.controllers;

import dk.eds.coursemanager.models.Booking;
import dk.eds.coursemanager.repositories.BookingRepository;
import dk.eds.coursemanager.repositories.CourseRepository;
import dk.eds.coursemanager.repositories.PersonRepository;
import dk.eds.coursemanager.repositories.RoomRepository;
import dk.eds.coursemanager.resources.BookingResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/bookings")
public class BookingController {

    private final BookingRepository bookingRepository;
    private final RoomRepository roomRepository;
    private final PersonRepository personRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public BookingController(BookingRepository bookingRepository, RoomRepository roomRepository, PersonRepository personRepository, CourseRepository courseRepository) {
        this.bookingRepository = bookingRepository;
        this.roomRepository = roomRepository;
        this.personRepository = personRepository;
        this.courseRepository = courseRepository;
    }

    @GetMapping
    public List<BookingResource> getAllBookings(@RequestParam(name = "from", required = false) Date from, @RequestParam(name = "to", required = false) Date to) {
        if (from != null && to != null)
            return bookingRepository.getAllByFromTimeAfterAndToTimeBefore(from, to).stream().map(BookingResource::new).collect(Collectors.toList());
        return bookingRepository.findAll().stream().map(BookingResource::new).collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity createBooking(@Valid @RequestBody Booking booking) {
        if (!roomRepository.exists(booking.getRoom().getId()) ||
                !courseRepository.exists(booking.getCourse().getId()) ||
                !personRepository.existsPersonByUser_Username(booking.getBooker().getUser().getUsername()))
            return ResponseEntity.notFound().build();
        return ResponseEntity.created(new BookingResource(bookingRepository.save(booking)).getSelfRelURI()).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<BookingResource> getBooking(@PathVariable("id") Long id) {
        if (!bookingRepository.exists(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new BookingResource(bookingRepository.findOne(id)));
    }

    @PutMapping("{id}")
    public ResponseEntity updateBooking(@Valid @RequestBody Booking booking, @PathVariable("id") Long id) {
        if (!bookingRepository.exists(id))
            return ResponseEntity.notFound().build();
        try {
            Booking b = bookingRepository.findOne(id);
            b.setBooker(personRepository.getPersonByUser_Username(booking.getBooker().getUser().getUsername()));
            b.setCourse(courseRepository.findOne(booking.getCourse().getId()));
            b.setRoom(roomRepository.findOne(booking.getRoom().getId()));
            b.setFromTime(booking.getFromTime());
            b.setToTime(booking.getToTime());
            return ResponseEntity.ok(new BookingResource(bookingRepository.save(b)));
        } catch (Exception ex) {
            return ResponseEntity.status(304).build();
        }
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteBooking(@PathVariable("id") Long id) {
        if (!bookingRepository.exists(id)) return ResponseEntity.notFound().build();
        try {
            bookingRepository.delete(id);
            return ResponseEntity.accepted().build();
        } catch (Exception ex) {
            return ResponseEntity.status(304).build();
        }
    }
}
