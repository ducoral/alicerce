package io.alicerce.core;

import io.alicerce.Messages;
import io.quarkus.qute.TemplateData;

import java.text.MessageFormat;
import java.util.Map;

@TemplateData
public class MessagesData implements Messages {

    private final Map<String, String> map;

    MessagesData(Map<String, String> map) {
        this.map = map;
    }

    public String msg(String key) {
        return map.getOrDefault(key, "(keyNotFound:%s)".formatted(key));
    }

    public String msg(String key, Object... args) {
        return MessageFormat.format(msg(key), args);
    }
}
