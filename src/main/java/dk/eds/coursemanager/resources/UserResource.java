package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.controllers.UserController;
import dk.eds.coursemanager.models.User;
import org.springframework.hateoas.ResourceSupport;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class UserResource extends ResourceSupport {

    private final User user;

    public UserResource(User user) {
        this.user = user;
        this.add(linkTo(methodOn(UserController.class).getUser(user.getUserType().getId(), user.getUsername())).withSelfRel());
    }

    public User getUser() {
        return user;
    }
}
