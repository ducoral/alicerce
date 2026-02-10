package io.alicerce.core;

import io.quarkus.qute.TemplateData;

import java.text.MessageFormat;
import java.util.Map;

@TemplateData
public class Messages {

    private final Map<String, String> map;

    Messages(Map<String, String> map) {
        this.map = map;
    }

    public String msg(String key) {
        return map.getOrDefault(key, "(keyNotFound:%s)".formatted(key));
    }

    public String msg(String key, Object... args) {
        return MessageFormat.format(msg(key), args);
    }
}
