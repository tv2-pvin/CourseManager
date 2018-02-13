package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.models.CourseType;
import org.springframework.hateoas.ResourceSupport;

public class CourseTypeResource extends ResourceSupport {

    private final CourseType courseType;

    public CourseTypeResource(CourseType courseType) {
        this.courseType = courseType;
    }

    public CourseType getCourseType() {
        return courseType;
    }
}
