package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.controllers.PersonController;
import dk.eds.coursemanager.models.Person;
import dk.eds.coursemanager.models.User;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class PersonResource extends ResourceSupportBase {

    private final Person person;

    public PersonResource(Person person) {
        this.person = person;
        this.add(linkTo(methodOn(PersonController.class).getPerson(person.getUser().getUsername())).withSelfRel());
    }

    public Person getPerson() {
        return person;
    }
}
