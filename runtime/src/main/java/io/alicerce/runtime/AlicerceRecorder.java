package io.alicerce.runtime;

import io.alicerce.core.MessagesByLanguage;
import io.alicerce.core.PagePath;
import io.quarkus.runtime.annotations.Recorder;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

@Recorder
public class AlicerceRecorder {

    public Supplier<MessagesByLanguage> createMessagesByLanguage(Map<String, Map<String, String>> messagesMap) {
        return () -> new MessagesByLanguage(messagesMap);
    }

    public Supplier<PagePath> createPagePath(List<String[]> splittedPaths) {
        return () -> {
            var pagePath = new PagePath();
            for (var splitted : splittedPaths)
                buildPagePath(splitted, 0, pagePath.paths);
            return pagePath;
        };
    }

    private static void buildPagePath(String[] splitted, int level, List<PagePath.Path> paths) {
        if (level == splitted.length)
            return;
        var code = splitted[level];
        var path = find(code, paths);
        if (path == null)
            paths.add(path = new PagePath.Path(code, new ArrayList<>()));
        buildPagePath(splitted, level + 1, path.children());
    }

    private static PagePath.Path find(String code, List<PagePath.Path> paths) {
        for (var path : paths)
            if (path.code().equals(code))
                return path;
        return null;
    }
}
