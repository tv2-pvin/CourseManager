package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.models.ParticipantType;
import org.springframework.hateoas.ResourceSupport;

public class ParticipantTypeResource extends ResourceSupportBase {

    private final ParticipantType participantType;

    public ParticipantTypeResource(ParticipantType participantType) {
        this.participantType = participantType;
    }

    public ParticipantType getParticipantType() {
        return participantType;
    }
}
