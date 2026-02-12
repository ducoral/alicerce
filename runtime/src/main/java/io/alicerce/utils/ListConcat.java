package io.alicerce.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class ListConcat<T> {

    List<T> list;

    public ListConcat(List<T> list) {
        this.list = new ArrayList<>(list);
    }

    public ListConcat<T> concat(T value) {
        list.add(value);
        return this;
    }

    public ListConcat<T> concat(List<T> list) {
        this.list.addAll(list);
        return this;
    }

    public List<T> end() {
        return list;
    }

    public Stream<T> stream() {
        return list.stream();
    }
}