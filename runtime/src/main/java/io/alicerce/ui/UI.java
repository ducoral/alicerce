package io.alicerce.ui;

import io.alicerce.Messages;
import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

@CheckedTemplate
public class UI {

    public static native TemplateInstance cool(Messages msg, String param);
    public static native TemplateInstance hello();

    @CheckedTemplate(basePath = "comp")
    public static class comp {
        public static native TemplateInstance field(String value);
    }
}
