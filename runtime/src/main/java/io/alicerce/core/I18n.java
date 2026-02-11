package io.alicerce.core;

import jakarta.inject.Inject;
import jakarta.ws.rs.core.UriInfo;

public class I18n {

    @Inject
    UriInfo uriInfo;

    @Inject
    PagePath pagePath;

    public String msg(String key) {
        return "requestPath:"
                + uriInfo.getRequestUri().getPath()
                + " pagePath: "
                + pagePath.toString()
                + " key: " + key;
    }
}
