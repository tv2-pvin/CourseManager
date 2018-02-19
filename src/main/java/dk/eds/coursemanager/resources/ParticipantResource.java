package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.models.Participant;

public class ParticipantResource extends ResourceSupportBase {

    private final Participant participant;

    public ParticipantResource(Participant participant) {
        this.participant = participant;
    }

    public Participant getParticipant() {
        return participant;
    }
}
