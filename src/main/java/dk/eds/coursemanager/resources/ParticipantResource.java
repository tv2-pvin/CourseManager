package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.controllers.CourseController;
import dk.eds.coursemanager.controllers.ParticipantController;
import dk.eds.coursemanager.controllers.ParticipantTypeController;
import dk.eds.coursemanager.controllers.PersonController;
import dk.eds.coursemanager.models.Participant;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class ParticipantResource extends ResourceSupportBase {

    private final Participant participant;

    public ParticipantResource(Participant participant) {
        this.participant = participant;
        this.add(
                linkTo(methodOn(ParticipantController.class).getParticipant(participant.getId())).withSelfRel(),
                linkTo(methodOn(CourseController.class).getCourse(participant.getCourse().getCourseType().getId(), participant.getCourse().getId())).withRel("course"),
                linkTo(methodOn(ParticipantTypeController.class).getParticipantType(participant.getParticipantType().getId())).withRel("participant-type"),
                linkTo(methodOn(PersonController.class).getPerson(participant.getPerson().getPersonType().getId(), participant.getPerson().getUser().getUsername())).withRel("person")
        );
    }

    public Participant getParticipant() {
        return participant;
    }
}
