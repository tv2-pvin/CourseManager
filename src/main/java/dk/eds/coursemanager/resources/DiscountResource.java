package dk.eds.coursemanager.resources;

import dk.eds.coursemanager.models.Discount;
import org.springframework.hateoas.ResourceSupport;

public class DiscountResource extends ResourceSupport {

    private final Discount discount;

    public DiscountResource(Discount discount) {
        this.discount = discount;
    }

    public Discount getDiscount() {
        return discount;
    }
}
