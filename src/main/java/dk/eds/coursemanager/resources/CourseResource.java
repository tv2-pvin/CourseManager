package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.controllers.CourseController;
import dk.eds.coursemanager.controllers.CourseTypeController;
import dk.eds.coursemanager.models.Course;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class CourseResource extends ResourceSupport {

    private final Course course;

    public CourseResource(Course course) {
        this.course = course;
        this.add(linkTo(methodOn(CourseController.class).getCourse(course.getId())).withSelfRel());
        this.add(linkTo(methodOn(CourseTypeController.class).getCourseType(course.getCourseType().getId())).withRel("course-type"));
    }

    public Course getCourse() {
        return course;
    }
}
