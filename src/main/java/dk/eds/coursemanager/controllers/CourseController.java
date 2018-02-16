package dk.eds.coursemanager.controllers;

import dk.eds.coursemanager.models.Course;
import dk.eds.coursemanager.repositories.CourseRepository;
import dk.eds.coursemanager.repositories.CourseTypeRepository;
import dk.eds.coursemanager.resources.CourseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/courses")
public class CourseController {

    private final CourseRepository courseRepository;
    private final CourseTypeRepository courseTypeRepository;

    @Autowired
    public CourseController(CourseRepository courseRepository, CourseTypeRepository courseTypeRepository) {
        this.courseRepository = courseRepository;
        this.courseTypeRepository = courseTypeRepository;
    }

    @GetMapping
    public List<CourseResource> getAllCourses() {
        return courseRepository.findAll().stream()
                .map(CourseResource::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity createCourse(@Valid @RequestBody Course course) {
        if (!courseTypeRepository.exists(course.getCourseType().getId()))
            return ResponseEntity.notFound().build();
        course = courseRepository.save(course);
        return ResponseEntity.ok(new CourseResource(course));
    }

    @GetMapping("{id}")
    public ResponseEntity<CourseResource> getCourse(@PathVariable("id") Long id) {
        if (!courseRepository.exists(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(new CourseResource(courseRepository.findOne(id)));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteCourse(@PathVariable("id") Long id) {
        if (courseRepository.exists(id)) return ResponseEntity.notFound().build();
        try {
            courseRepository.delete(id);
            return ResponseEntity.accepted().build();
        } catch (Exception ex) {
            return ResponseEntity.status(304).build();
        }
    }

    @PutMapping("{id}")
    public ResponseEntity updateCourse(@PathVariable("id") Long id, @Valid @RequestBody Course course) {
        if (!courseRepository.exists(id)) return ResponseEntity.notFound().build();
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
}
