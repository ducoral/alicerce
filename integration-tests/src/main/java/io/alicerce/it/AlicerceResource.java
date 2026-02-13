package io.alicerce.it;

import io.alicerce.ui.CoolFeature;
import io.alicerce.ui.Menu;
import io.alicerce.ui.Resource;
import io.alicerce.ui.UI;
import io.quarkus.logging.Log;
import io.quarkus.qute.TemplateInstance;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/hello")
@ApplicationScoped
@Produces(MediaType.TEXT_PLAIN)
public class AlicerceResource extends Resource implements CoolFeature {

    @Inject
    Menu menu;

    @Override
    protected TemplateInstance getTemplate() {
        Log.info("\n" + menu);
        return UI.hello().data("menu", menu);
    }
}
