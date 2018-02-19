package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.models.ParticipantTypeCoursePrice;

public class ParticipantTypeCoursePriceResource extends ResourceSupportBase {

    private final ParticipantTypeCoursePrice participantTypeCoursePrice;

    public ParticipantTypeCoursePriceResource(ParticipantTypeCoursePrice participantTypeCoursePrice) {
        this.participantTypeCoursePrice = participantTypeCoursePrice;
    }

    public ParticipantTypeCoursePrice getParticipantTypeCoursePrice() {
        return participantTypeCoursePrice;
    }
}
