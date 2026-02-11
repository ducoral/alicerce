package io.alicerce.it;

import io.alicerce.core.I18n;
import io.alicerce.ui.CoolFeature;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/")
@ApplicationScoped
@Produces(MediaType.TEXT_PLAIN)
public class AlicerceResource implements CoolFeature {

    @Inject
    I18n i18n;

    @GET
    public String hello() {
        return "Hello HOME " + i18n.msg("opa");
    }

    @GET
    @Path("/alicerce")
    public String alicerce() {
        return "Hello Alicerce " + i18n.msg("opa");
    }
}
