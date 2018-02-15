package dk.eds.coursemanager.controllers;

import dk.eds.coursemanager.models.Course;
import dk.eds.coursemanager.repositories.CourseRepository;
import dk.eds.coursemanager.repositories.CourseTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    public List<Course> getAllCourses() {
        return courseRepository.findAll();
    }

    @PostMapping
    public ResponseEntity createCourse(@Valid @RequestBody Course course) {
        if (!courseTypeRepository.exists(course.getCourseType().getId()))
            return ResponseEntity.notFound().build();
        course = courseRepository.save(course);
        return ResponseEntity.ok(course);
    }
}
