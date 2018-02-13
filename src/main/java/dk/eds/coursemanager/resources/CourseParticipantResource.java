package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.models.CourseParticipant;
import org.springframework.hateoas.ResourceSupport;

public class CourseParticipantResource extends ResourceSupport {

    private final CourseParticipant courseParticipant;

    public CourseParticipantResource(CourseParticipant courseParticipant) {
        this.courseParticipant = courseParticipant;
    }

    public CourseParticipant getCourseParticipant() {
        return courseParticipant;
    }
}
