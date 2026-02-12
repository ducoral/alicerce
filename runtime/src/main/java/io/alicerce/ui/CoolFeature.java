package io.alicerce.ui;

import io.alicerce.Messages;
import io.quarkus.qute.TemplateInstance;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

public interface CoolFeature {

    Messages getMessages();

    @GET
    @Path("/cool")
    @Produces(MediaType.TEXT_HTML)
    default TemplateInstance cool() {
        return UI.cool(getMessages(), "param-teste");
    }
}
