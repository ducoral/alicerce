package io.alicerce.utils;

import java.util.Locale;

public class Utils {

    public static String[] splitPath(String path) {
        path = path.trim();
        while (path.startsWith("/"))
            path = path.substring(1);
        while (path.endsWith("/"))
            path = path.substring(0, path.length() - 1);
        return path.split("/");
    }

    public static Locale parseLanguage(String language) {
        var languageCountry = language.split("_");
        return languageCountry.length == 2
                ? Locale.of(languageCountry[0], languageCountry[1])
                : Locale.of(language);
    }
}