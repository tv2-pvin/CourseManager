package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.controllers.ParticipantTypeController;
import dk.eds.coursemanager.models.ParticipantType;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ParticipantTypeResource extends ResourceSupportBase {

    private final ParticipantType participantType;

    public ParticipantTypeResource(ParticipantType participantType) {
        this.participantType = participantType;
        this.add(
                linkTo(methodOn(ParticipantTypeController.class).getParticipantType(participantType.getId())).withSelfRel(),
                linkTo(methodOn(ParticipantTypeController.class).getParticipants(participantType.getId())).withRel("participants"),
                linkTo(methodOn(ParticipantTypeController.class).getPrices(participantType.getId())).withRel("prices")
        );
    }

    public ParticipantType getParticipantType() {
        return participantType;
    }
}
