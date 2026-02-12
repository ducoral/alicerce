package io.alicerce.core;

import io.alicerce.Messages;
import jakarta.inject.Inject;
import jakarta.ws.rs.core.HttpHeaders;

public class I18n implements Messages {

    @Inject
    HttpHeaders httpHeaders;

    @Inject
    MessagesProvider messagesProvider;

    @Override
    public String msg(String key) {
        return getMessages()
                .msg(key);
    }

    @Override
    public String msg(String key, Object... args) {
        return getMessages()
                .msg(key, args);
    }

    public Messages getMessages() {
        return messagesProvider
                .find(httpHeaders.getAcceptableLanguages());
    }
}
