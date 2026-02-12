package io.alicerce;

public interface Messages {

    String msg(String key);

    String msg(String key, Object... args);
}
