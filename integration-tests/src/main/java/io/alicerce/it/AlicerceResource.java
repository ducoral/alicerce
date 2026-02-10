package io.alicerce.it;

import io.alicerce.core.PagePath;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@ApplicationScoped
@Produces(MediaType.TEXT_PLAIN)
public class AlicerceResource {

    @Inject
    PagePath pagePath;

    @GET
    public String hello() {
        return "Hello HOME " + pagePath;
    }

    @GET
    @Path("/alicerce")
    public String alicerce() {
        return "Hello Alicerce " + pagePath;
    }
}
