package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.controllers.UserController;
import dk.eds.coursemanager.models.UserType;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class UserTypeResource extends ResourceSupport {

    private final UserType userType;

    public UserTypeResource(UserType userType) {
        this.userType = userType;
        this.add(linkTo(methodOn(UserController.class).getUserType(userType.getId())).withSelfRel());
        this.add(linkTo(methodOn(UserController.class).getAllUsersForUserType(userType.getId())).withRel("users"));
    }

    public UserType getUserType() {
        return userType;
    }
}
