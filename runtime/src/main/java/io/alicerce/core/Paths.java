package io.alicerce.core;

import io.alicerce.utils.Node;

import java.util.List;
import java.util.function.Consumer;

public class Paths {

    private final List<Node> nodes;

    public Paths(List<Node> nodes) {
        this.nodes = nodes;
    }

    public List<Node> getNodes() {
        return nodes;
    }

    public void forEach(Consumer<Node> action) {
        nodes.forEach(action);
    }

    @Override
    public String toString() {
        return Node.toString(nodes);
    }
}