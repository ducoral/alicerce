package io.alicerce.runtime;

import io.alicerce.core.PagePath;
import io.quarkus.runtime.annotations.Recorder;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Recorder
public class PagePathRecorder {

    public Supplier<PagePath> buildPagePath(List<String[]> splittedPaths) {
        return () -> {
            var pagePath = new PagePath();
            for (var splitted : splittedPaths)
                build(splitted, 0, pagePath.paths);
            return pagePath;
        };
    }

    private static void build(String[] splitted, int level, List<PagePath.Path> paths) {
        if (level == splitted.length)
            return;
        var code = splitted[level];
        var path = find(code, paths);
        if (path == null)
            paths.add(path = new PagePath.Path(code, new ArrayList<>()));
        build(splitted, level + 1, path.children());
    }

    private static PagePath.Path find(String code, List<PagePath.Path> paths) {
        for (var path : paths)
            if (path.code().equals(code))
                return path;
        return null;
    }
}
