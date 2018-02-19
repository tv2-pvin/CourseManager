package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.controllers.CourseController;
import dk.eds.coursemanager.controllers.ParticipantTypeController;
import dk.eds.coursemanager.controllers.PriceController;
import dk.eds.coursemanager.models.Price;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class PriceResource extends ResourceSupportBase {

    private final Price price;

    public PriceResource(Price price) {
        this.price = price;
        this.add(
                linkTo(methodOn(PriceController.class).getPrice(price.getId())).withSelfRel(),
                linkTo(methodOn(CourseController.class).getCourse(price.getCourse().getCourseType().getId(), price.getCourse().getId())).withRel("course"),
                linkTo(methodOn(ParticipantTypeController.class).getParticipantType(price.getParticipantType().getId())).withRel("participant-type")
        );
    }

    public Price getPrice() {
        return price;
    }
}
