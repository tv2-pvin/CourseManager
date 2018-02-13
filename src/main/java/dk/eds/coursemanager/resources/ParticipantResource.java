package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.models.Participant;
import org.springframework.hateoas.ResourceSupport;

public class ParticipantResource extends ResourceSupport {

    private final Participant participant;

    public ParticipantResource(Participant participant) {
        this.participant = participant;
    }

    public Participant getParticipant() {
        return participant;
    }
}
