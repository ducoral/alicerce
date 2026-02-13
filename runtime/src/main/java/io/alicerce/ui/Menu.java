package io.alicerce.ui;

import io.alicerce.utils.Node;
import io.alicerce.utils.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Menu {

    public record Item(
            String id,
            String parent,
            String path,
            String label,
            int depth,
            boolean isLeaf) {

        public boolean isChildOf(Item item) {
            return parent.equals(item.id());
        }

        public boolean isParentOf(Item item) {
            return id.equals(item.parent());
        }
    }

    public interface Tree {

        default String id() {
            return "root";
        }

        default String path() {
            return "/";
        }

        default String label() {
            return "";
        }

        default int depth() {
            return 0;
        }

        default boolean isLeaf() {
            return nodes().isEmpty();
        }

        default void forEach(Consumer<Tree> action) {
            nodes().forEach(action);
        }

        List<Tree> nodes();

        Item menuItem();
    }

    private final List<Item> items;

    private Menu(List<Item> items) {
        this.items = items;
    }

    public static Menu of(Node node) {
        var items = new ArrayList<Item>();
        node.forEach(item -> build(items, item, "/"));
        return new Menu(items);
    }

    public Tree tree() {
        return new TreeImpl(this);
    }

    public Stream<Item> items() {
        return items.stream();
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
        var label = "label"
                .concat(path)
                .replace("/", ".");
        items.add(new Item(id, parent, path, label, node.getDepth(), node.isLeaf()));
        node.forEach(child -> build(items, child, path));
    }

    private record TreeImpl(Menu menu) implements Tree {

        @Override
            public List<Tree> nodes() {
                var nodes = new ArrayList<Tree>();
                menu
                        .items()
                        .forEach(item -> nodes.add(build(menu, item)));
                return nodes;
            }

            @Override
            public Item menuItem() {
                return new Item("root", "", "/", "", 0, isLeaf());
            }

            @Override
            public String toString() {
                final var str = new StringBuilder("root\n");
                nodes().forEach(node -> build(str, node));
                return str.toString();
            }

            private static void build(StringBuilder str, Tree tree) {
                str.append("%s%s\n".formatted(
                        Utils.spaces(tree.depth() * 2),
                        tree.menuItem()));
                tree.forEach(node -> build(str, node));
            }

            private static Tree build(Menu menu, Item item) {
                return new Tree() {

                    @Override
                    public String id() {
                        return item.id();
                    }

                    @Override
                    public String path() {
                        return item.path();
                    }

                    @Override
                    public String label() {
                        return item.label();
                    }

                    @Override
                    public int depth() {
                        return item.depth();
                    }

                    @Override
                    public List<Tree> nodes() {
                        return TreeImpl.nodes(menu, item);
                    }

                    @Override
                    public Item menuItem() {
                        return item;
                    }
                };
            }

            private static List<Tree> nodes(Menu menu, Item item) {
                var nodes = new ArrayList<Tree>();
                menu
                        .items()
                        .filter(item::isParentOf)
                        .forEach(node -> nodes.add(build(menu, node)));
                return nodes;
            }
        }
}