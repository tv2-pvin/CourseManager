package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.controllers.PersonTypeController;
import dk.eds.coursemanager.models.PersonType;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class PersonTypeResource extends ResourceSupportBase {

    private final PersonType personType;

    public PersonTypeResource(PersonType personType) {
        this.personType = personType;
        this.add(linkTo(methodOn(PersonTypeController.class).getPersonType(personType.getId())).withSelfRel());
        this.add(linkTo(methodOn(PersonTypeController.class).getAllPeopleForPersonType(personType.getId())).withRel("users"));
    }

    public PersonType getPersonType() {
        return personType;
    }
}
