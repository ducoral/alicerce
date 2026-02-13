package io.alicerce.core;

import io.alicerce.utils.Node;

public class Paths {

    private final Node node;

    public Paths(Node node) {
        this.node = node;
    }

    @Override
    public String toString() {
        return node.toString();
    }
}