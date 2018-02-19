package dk.eds.coursemanager.resources;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.hateoas.ResourceSupport;

import java.net.URI;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;


public class ResourceSupportBase extends ResourceSupport {

    @JsonIgnore
    public URI getSelfRelURI() {
        return URI.create(this.getLink("self").getHref());
    }
}
