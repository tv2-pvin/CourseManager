package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.controllers.CourseTypeController;
import dk.eds.coursemanager.controllers.DiscountController;
import dk.eds.coursemanager.controllers.ParticipantTypeController;
import dk.eds.coursemanager.models.Discount;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

public class DiscountResource extends ResourceSupportBase {

    private final Discount discount;

    public DiscountResource(Discount discount) {
        this.discount = discount;
        this.add(
                linkTo(methodOn(DiscountController.class).getDiscount(discount.getId())).withSelfRel(),
                linkTo(methodOn(CourseTypeController.class).getCourseType(discount.getCourseType().getId())).withRel("course-type"),
                linkTo(methodOn(ParticipantTypeController.class).getParticipantType(discount.getParticipantType().getId())).withRel("participant-type")
        );
    }

    public Discount getDiscount() {
        return discount;
    }
}
