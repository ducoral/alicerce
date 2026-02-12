package io.alicerce.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;

public class Utils {

    public static String[] splitPath(String path) {
        path = path.trim();
        while (path.startsWith("/"))
            path = path.substring(1);
        while (path.endsWith("/"))
            path = path.substring(0, path.length() - 1);
        return path.split("/");
    }

    public static String spaces(int size) {
        return new String(new char[size])
                .replace('\0', ' ');
    }

    public static Locale parseLanguage(String language) {
        var array = language.split("_");
        return array.length == 2
                ? Locale.of(array[0], array[1])
                : Locale.of(array[0]);
    }

    public static InputStream resourceAsStream(String resource) {
        return Thread
                .currentThread()
                .getContextClassLoader()
                .getResourceAsStream(resource);
    }

    public static Map<String, String> loadProperties(InputStream inputStream) throws IOException {
        var map = new HashMap<String, String>();
        var properties = new Properties();
        properties.load(inputStream);
        for (var key : properties.keySet()) {
            var keyStr = String.valueOf(key);
            map.put(keyStr, properties.getProperty(keyStr));
        }
        return map;
    }

    public static <T> ListConcat<T> concat(List<T> list) {
        return new ListConcat<>(list);
    }
}