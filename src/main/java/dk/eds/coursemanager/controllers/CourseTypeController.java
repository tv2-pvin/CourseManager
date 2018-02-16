package dk.eds.coursemanager.controllers;

import dk.eds.coursemanager.models.CourseType;
import dk.eds.coursemanager.repositories.CourseRepository;
import dk.eds.coursemanager.repositories.CourseTypeRepository;
import dk.eds.coursemanager.resources.CourseResource;
import dk.eds.coursemanager.resources.CourseTypeResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/course-types")
public class CourseTypeController {

    private final CourseTypeRepository courseTypeRepository;
    private final CourseRepository courseRepository;

    @Autowired
    public CourseTypeController(CourseTypeRepository courseTypeRepository, CourseRepository courseRepository) {
        this.courseTypeRepository = courseTypeRepository;
        this.courseRepository = courseRepository;
    }


    @GetMapping
    public List<CourseTypeResource> getAllCourseTypes() {
        return courseTypeRepository.findAll().stream()
                .map(CourseTypeResource::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public CourseTypeResource createCourseType(@Valid @RequestBody CourseType courseType) {
        return new CourseTypeResource(courseTypeRepository.save(courseType));
    }

    @GetMapping("{id}")
    public CourseTypeResource getCourseType(@PathVariable("id") Long id) {
        return new CourseTypeResource(courseTypeRepository.findOne(id));
    }

    @DeleteMapping("{id}")
    public ResponseEntity deleteCourseType(@PathVariable("id") Long id) {
        try {
            courseTypeRepository.delete(id);
            return ResponseEntity.accepted().build();
        } catch (Exception e) {
            return ResponseEntity.status(304).build();
        }
    }

    @PutMapping("id")
    public ResponseEntity updateCourseType(@PathVariable("id") Long id, @Valid @RequestBody CourseType update) {
        if (!courseTypeRepository.exists(id)) return ResponseEntity.notFound().build();
        try {
            CourseType original = courseTypeRepository.findOne(id);
            original.setTypeName(update.getTypeName());
            courseTypeRepository.save(original);
            return ResponseEntity.accepted().build();
        } catch (Exception ex) {
            return ResponseEntity.status(304).build();
        }
    }

    @GetMapping("{id}/courses")
    public ResponseEntity<List<CourseResource>> getCoursesByCourseType(@PathVariable("id") Long id) {
        if (!courseTypeRepository.exists(id)) return ResponseEntity.notFound().build();
        List<CourseResource> courseResources = courseRepository.findAllByCourseType(courseTypeRepository.findOne(id))
                .stream()
                .map(CourseResource::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(courseResources);
    }
}
