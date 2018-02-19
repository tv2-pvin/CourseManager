package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.controllers.CourseController;
import dk.eds.coursemanager.controllers.CourseTypeController;
import dk.eds.coursemanager.models.Course;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class CourseResource extends ResourceSupportBase {

    private final Course course;

    public CourseResource(Course course) {
        this.course = course;
        this.add(
                linkTo(methodOn(CourseController.class).getCourse(course.getCourseType().getId(), course.getId())).withSelfRel(),
                linkTo(methodOn(CourseTypeController.class).getCourseType(course.getCourseType().getId())).withRel("course-type"),
                linkTo(methodOn(CourseController.class).getCourseBookings(course.getCourseType().getId(), course.getId())).withRel("bookings"),
                linkTo(methodOn(CourseController.class).getCourseParticipants(course.getCourseType().getId(), course.getId())).withRel("participants"),
                linkTo(methodOn(CourseController.class).getCoursePrices(course.getCourseType().getId(), course.getId())).withRel("prices")
        );
    }

    public Course getCourse() {
        return course;
    }
}
