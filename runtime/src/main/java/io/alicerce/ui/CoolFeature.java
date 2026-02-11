package io.alicerce.ui;

import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

public interface CoolFeature {

    @GET
    @Path("/cool")
    @Produces(MediaType.TEXT_HTML)
    default TemplateInstance cool() {
        return UI.cool("Teste");
    }

    @GET
    @Path("/input")
    @Produces(MediaType.TEXT_HTML)
    default TemplateInstance input() {
        return UI.comp.field("Teste");
    }
}
