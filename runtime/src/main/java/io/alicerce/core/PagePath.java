package io.alicerce.core;

import java.util.ArrayList;
import java.util.List;

public class PagePath {

    public final List<Path> paths = new ArrayList<>();

    public record Path(String code, List<Path> children) {
    }

    @Override
    public String toString() {
        var str = new StringBuilder();
        for (var path : paths)
            str.append(path.toString()).append("\n");
        return str.toString();
    }
}
