package io.alicerce.ui;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

@CheckedTemplate
public class UI {

    public static native TemplateInstance cool(String param);

    @CheckedTemplate(basePath = "comp")
    public static class comp {
        public static native TemplateInstance field(String value);
    }
}
