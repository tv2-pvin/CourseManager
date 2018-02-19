package dk.eds.coursemanager.resources;

import org.springframework.hateoas.ResourceSupport;

import java.net.URI;

public class ResourceSupportBase extends ResourceSupport {

    public URI getSelfRelURI() {
        return URI.create(this.getLink("self").getHref());
    }
}
