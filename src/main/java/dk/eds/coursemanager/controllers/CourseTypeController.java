package dk.eds.coursemanager.controllers;

import dk.eds.coursemanager.models.CourseType;
import dk.eds.coursemanager.repositories.CourseTypeRepository;
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

    @Autowired
    public CourseTypeController(CourseTypeRepository courseTypeRepository) {
        this.courseTypeRepository = courseTypeRepository;
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
}
