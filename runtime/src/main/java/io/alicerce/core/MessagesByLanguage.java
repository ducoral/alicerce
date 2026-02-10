package io.alicerce.core;

import io.alicerce.utils.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MessagesByLanguage {

    private final Map<Locale, Messages> map = new HashMap<>();

    public MessagesByLanguage(Map<String, Map<String, String>> map) {
        for (var language : map.keySet())
            this.map.put(
                    Utils.parseLanguage(language),
                    new Messages(map.get(language)));
    }

    public Messages find(List<Locale> acceptableLanguages) {
        for (var language : acceptableLanguages)
            if (map.containsKey(language))
                return map.get(language);
        return map.get(Locale.of("default"));
    }
}
