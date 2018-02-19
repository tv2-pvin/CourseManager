package dk.eds.coursemanager.controllers;

import dk.eds.coursemanager.models.Course;
import dk.eds.coursemanager.repositories.*;
import dk.eds.coursemanager.resources.BookingResource;
import dk.eds.coursemanager.resources.CourseResource;
import dk.eds.coursemanager.resources.ParticipantResource;
import dk.eds.coursemanager.resources.PriceResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/course-types/{courseTypeId}/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final CourseTypeRepository courseTypeRepository;
    private final ParticipantRepository participantRepository;
    private final BookingRepository bookingRepository;
    private final PriceRepository priceRepository;

    @Autowired
    public CourseController(CourseRepository courseRepository, CourseTypeRepository courseTypeRepository, ParticipantRepository participantRepository, BookingRepository bookingRepository, PriceRepository priceRepository) {
        this.courseRepository = courseRepository;
        this.courseTypeRepository = courseTypeRepository;
        this.participantRepository = participantRepository;
        this.bookingRepository = bookingRepository;
        this.priceRepository = priceRepository;
    }

    @GetMapping
    public List<CourseResource> getAllCoursesForType(@PathVariable("courseTypeId") Long id) {
        return courseRepository.findAllByCourseType_Id(id).stream()
                .map(CourseResource::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity createCourse(@PathVariable("courseTypeId") Long id, @Valid @RequestBody Course course) {
        if (!courseTypeRepository.exists(id))
            return ResponseEntity.notFound().build();
        course.setCourseType(courseTypeRepository.findOne(id));
        course = courseRepository.save(course);
        return ResponseEntity.created(new CourseResource(course).getSelfRelURI()).build();
    }

    @GetMapping("{id}")
    public ResponseEntity<CourseResource> getCourse(@PathVariable("courseTypeId") Long courseTypeId, @PathVariable("id") Long id) {
        if (!courseTypeRepository.exists(courseTypeId) || !courseRepository.exists(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new CourseResource(courseRepository.findOne(id)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteCourse(@PathVariable("courseTypeId") Long courseTypeId, @PathVariable("id") Long id) {
        if (!courseTypeRepository.exists(courseTypeId) || !courseRepository.exists(id))
            return ResponseEntity.notFound().build();
        try {
            courseRepository.delete(id);
            return ResponseEntity.accepted().build();
        } catch (Exception ex) {
            return ResponseEntity.status(304).build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity updateCourse(@PathVariable("courseTypeId") Long courseTypeId, @PathVariable("id") Long id, @Valid @RequestBody Course course) {
        if (!courseTypeRepository.exists(courseTypeId) || !courseRepository.exists(id))
            return ResponseEntity.notFound().build();
        try {
            Course original = courseRepository.findOne(id);
            original.setCourseType(courseTypeRepository.findOne(course.getCourseType().getId()));
            original.setName(course.getName());
            courseRepository.save(course);
            return ResponseEntity.accepted().build();
        } catch (Exception ex) {
            return ResponseEntity.status(304).build();
        }
    }

    @GetMapping("{id}/participants")
    public ResponseEntity<List<ParticipantResource>> getCourseParticipants(@PathVariable("courseTypeId") Long courseTypeId, @PathVariable("id") Long id) {
        if (!courseTypeRepository.exists(courseTypeId) || !courseRepository.exists(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                participantRepository.findAllByCourse_Id(id).stream().map(ParticipantResource::new).collect(Collectors.toList())
        );
    }

    @GetMapping("{id}/bookings")
    public ResponseEntity<List<BookingResource>> getCourseBookings(@PathVariable("courseTypeId") Long courseTypeId, @PathVariable("id") Long id) {
        if (!courseTypeRepository.exists(courseTypeId) || !courseRepository.exists(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                bookingRepository.getAllByCourse_Id(id).stream().map(BookingResource::new).collect(Collectors.toList())
        );
    }

    @GetMapping("{id}/prices")
    public ResponseEntity<List<PriceResource>> getCoursePrices(@PathVariable("courseTypeId") Long courseTypeId, @PathVariable("id") Long id) {
        if (!courseTypeRepository.exists(courseTypeId) || !courseRepository.exists(id))
            return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                priceRepository.getAllByCourse_Id(id).stream().map(PriceResource::new).collect(Collectors.toList())
        );
    }
}
