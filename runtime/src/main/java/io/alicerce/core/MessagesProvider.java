package io.alicerce.core;

import io.alicerce.Messages;
import io.alicerce.utils.Utils;
import io.quarkus.runtime.configuration.ConfigurationException;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class MessagesProvider {
    static final List<String> ALICERCE_LANGUAGES = List.of("pt_BR", "es_ES");

    final Map<Locale, Messages> messagesMap = new HashMap<>();

    MessagesProvider(Map<String, Map<String, String>> messagesMap) {
        for (var language : messagesMap.keySet()) {
            this.messagesMap.put(
                    Utils.parseLanguage(language),
                    new MessagesData(messagesMap.get(language)));
        }
    }

    public static MessagesProvider of(List<String> languages, List<String> baseNames) {
        var messagesMap = new HashMap<String, Map<String, String>>();
        for (var language : Utils.concat(ALICERCE_LANGUAGES).concat("").end())
            load(language, "io.alicerce.messages", messagesMap);
        for (var language : Utils.concat(languages).concat("").end())
            for (var baseName : baseNames)
                load(language, baseName, messagesMap);
        return new MessagesProvider(messagesMap);
    }

    public Messages find(List<Locale> acceptableLanguages) {
        for (var locale : acceptableLanguages) {
            if (messagesMap.containsKey(locale))
                return messagesMap.get(locale);
            if (!locale.getCountry().isEmpty()) {
                var onlyLanguage = Locale.of(locale.getLanguage());
                if (messagesMap.containsKey(onlyLanguage))
                    return messagesMap.get(onlyLanguage);
            }
        }
        return messagesMap.get(Locale.of(""));
    }

    static void load(String language, String baseName, Map<String, Map<String, String>> messagesMap) {
        var resource = buildName(baseName, language);
        try (var inputStream = Utils.resourceAsStream(resource)) {
            if (inputStream == null)
                throw new ConfigurationException("Resource not found: %s".formatted(resource));
            messagesMap
                    .computeIfAbsent(language, key -> new HashMap<>())
                    .putAll(Utils.loadProperties(inputStream));
        } catch (IOException e) {
            throw new ConfigurationException(e);
        }
    }

    static String buildName(String baseName, String language) {
        if (!baseName.startsWith("/"))
            baseName = "/".concat(baseName);
        if (!language.isEmpty())
            language = "_".concat(language);
        return baseName
                .concat(language)
                .concat(".properties");
    }
}