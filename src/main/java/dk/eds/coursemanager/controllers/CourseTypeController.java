package dk.eds.coursemanager.controllers;

import dk.eds.coursemanager.models.CourseType;
import dk.eds.coursemanager.repositories.CourseTypeRepository;
import dk.eds.coursemanager.repositories.DiscountRepository;
import dk.eds.coursemanager.resources.CourseTypeResource;
import dk.eds.coursemanager.resources.DiscountResource;
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
    private final DiscountRepository discountRepository;

    @Autowired
    public CourseTypeController(CourseTypeRepository courseTypeRepository, DiscountRepository discountRepository) {
        this.courseTypeRepository = courseTypeRepository;
        this.discountRepository = discountRepository;
    }


    @GetMapping
    public List<CourseTypeResource> getAllCourseTypes() {
        return courseTypeRepository.findAll().stream()
                .map(CourseTypeResource::new)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ResponseEntity createCourseType(@Valid @RequestBody CourseType courseType) {
        return ResponseEntity.created(new CourseTypeResource(courseTypeRepository.save(courseType)).getSelfRelURI()).build();
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

    @PutMapping("{id}")
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

    @GetMapping("{id}/discounts")
    public ResponseEntity<List<DiscountResource>> getDiscountsByCourseType(@PathVariable("id") Long id) {
        if (!courseTypeRepository.exists(id)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(
                discountRepository.getDiscountsByCourseType_Id(id).stream().map(DiscountResource::new).collect(Collectors.toList())
        );
    }
}
