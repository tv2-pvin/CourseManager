package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.controllers.CourseTypeController;
import dk.eds.coursemanager.models.CourseType;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class CourseTypeResource extends ResourceSupport {

    private final CourseType courseType;

    public CourseTypeResource(CourseType courseType) {
        this.courseType = courseType;
        this.add(linkTo(methodOn(CourseTypeController.class).getCourseType(courseType.getId())).withSelfRel());
    }

    public CourseType getCourseType() {
        return courseType;
    }
}
