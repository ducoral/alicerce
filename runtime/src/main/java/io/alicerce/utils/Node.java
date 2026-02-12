package io.alicerce.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class Node {

    private final String code;

    private final int depth;

    private final List<Node> nodes = new ArrayList<>();

    public Node(String code, int depth) {
        this.code = code;
        this.depth = depth;
    }

    public static List<Node> of(List<String[]> pathsArray) {
        var nodes = new ArrayList<Node>();
        for (var path : pathsArray)
            parsePath(nodes, path, 0);
        return nodes;
    }

    public static Node find(List<Node> nodes, String code) {
        for (var node : nodes)
            if (node.isOf(code))
                return node;
        return null;
    }

    public String getCode() {
        return code;
    }

    public int getDepth() {
        return depth;
    }

    public void forEach(Consumer<Node> action) {
        nodes.forEach(action);
    }

    public boolean isOf(String code) {
        return this.code.equals(code);
    }

    public boolean isLeaf() {
        return nodes.isEmpty();
    }

    public static String toString(List<Node> nodes) {
        var str = new StringBuilder();
        toString(str, nodes, 0);
        return str.toString();
    }

    private static void toString(StringBuilder str, List<Node> nodes, int indent) {
        for (var node : nodes) {
            str
                    .append(Utils.spaces(indent))
                    .append(node.code)
                    .append('\n');
            toString(str, node.nodes, indent + 2);
        }
    }

    private static void parsePath(List<Node> nodes, String[] path, int index) {
        if (index == path.length)
            return;
        var code = path[index];
        var node = find(nodes, code);
        if (node == null)
            nodes.add(node = new Node(code, index));
        parsePath(node.nodes, path, index + 1);
    }
}
