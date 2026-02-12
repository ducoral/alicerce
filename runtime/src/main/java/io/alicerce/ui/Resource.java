package io.alicerce.ui;

import io.alicerce.Messages;
import io.alicerce.core.I18n;
import io.quarkus.qute.TemplateInstance;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

public abstract class Resource {

    @Inject
    I18n i18n;

    @GET
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance render() {
        return getTemplate()
                .data("msg", getMessages());
    }

    public Messages getMessages() {
        return i18n;
    }

    protected abstract TemplateInstance getTemplate();

    protected String msg(String key) {
        return i18n.msg(key);
    }

    protected String msg(String key, Object... args) {
        return i18n.msg(key, args);
    }
}
