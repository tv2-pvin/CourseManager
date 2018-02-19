package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.controllers.CourseController;
import dk.eds.coursemanager.controllers.CourseTypeController;
import dk.eds.coursemanager.models.CourseType;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class CourseTypeResource extends ResourceSupportBase {

    private final CourseType courseType;

    public CourseTypeResource(CourseType courseType) {
        this.courseType = courseType;
        this.add(
                linkTo(methodOn(CourseTypeController.class).getCourseType(courseType.getId())).withSelfRel(),
                linkTo(methodOn(CourseController.class).getAllCoursesForType(courseType.getId())).withRel("courses"),
                linkTo(methodOn(CourseTypeController.class).getDiscountsByCourseType(courseType.getId())).withRel("discounts")
        );
    }

    public CourseType getCourseType() {
        return courseType;
    }
}
