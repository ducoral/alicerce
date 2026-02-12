package io.alicerce.ui;

import io.alicerce.utils.Node;

import java.util.ArrayList;
import java.util.List;

public class Menu {

    public record Item(
            String id,
            String parent,
            String path,
            String key,
            int depth,
            boolean isLeaf) {
    }

    private final List<Item> items;

    private Menu(List<Item> items) {
        this.items = items;
    }

    public static Menu of(List<Node> nodes) {
        var items = new ArrayList<Item>();
        nodes.forEach(node -> build(items, node, "/"));
        return new Menu(items);
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public String toString() {
        var str = new StringBuilder();
        items.forEach(item ->
                str.append(item).append('\n'));
        return str.toString();
    }

    private static void build(
            List<Item> items,
            Node node,
            String parentPath) {
        var code = node.getCode();
        var isRoot = parentPath.equals("/");
        var parent = isRoot
                ? "root"
                : "mi".concat(parentPath).replace('/', '-');
        var path = isRoot
                ? parentPath.concat(code)
                : parentPath.concat("/").concat(code);
        var id = isRoot
                ? "mi-".concat(code)
                : "mi".concat(path).replace('/', '-');
        var key = "label"
                .concat(path)
                .replace("/", ".");
        items.add(new Item(id, parent, path, key, node.getDepth(), node.isLeaf()));
        node.forEach(child -> build(items, child, path));
    }
}