package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.controllers.PersonController;
import dk.eds.coursemanager.models.Person;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class PersonResource extends ResourceSupportBase {

    private final Person person;

    public PersonResource(Person person) {
        this.person = person;
        this.add(
                linkTo(methodOn(PersonController.class).getPerson(person.getPersonType().getId(), person.getUser().getUsername())).withSelfRel(),
                linkTo(methodOn(PersonController.class).getBookings(person.getPersonType().getId(), person.getUser().getUsername())).withRel("bookings"),
                linkTo(methodOn(PersonController.class).getParticipants(person.getPersonType().getId(), person.getUser().getUsername())).withRel("participants")
        );
    }

    public Person getPerson() {
        return person;
    }
}
