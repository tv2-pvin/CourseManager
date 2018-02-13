package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.models.Course;
import org.springframework.hateoas.ResourceSupport;

public class CourseResource extends ResourceSupport {

    private final Course course;

    public CourseResource(Course course) {
        this.course = course;
    }

    public Course getCourse() {
        return course;
    }
}
