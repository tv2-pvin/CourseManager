package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.models.CourseParticipantTypePrice;

public class CourseParticipantTypePriceResource {

    private final CourseParticipantTypePrice courseParticipantTypePrice;

    public CourseParticipantTypePriceResource(CourseParticipantTypePrice courseParticipantTypePrice) {
        this.courseParticipantTypePrice = courseParticipantTypePrice;
    }

    public CourseParticipantTypePrice getCourseParticipantTypePrice() {
        return courseParticipantTypePrice;
    }
}
