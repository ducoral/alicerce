package io.alicerce.runtime;

import io.alicerce.core.MessagesProvider;
import io.alicerce.core.Paths;
import io.alicerce.ui.Menu;
import io.alicerce.utils.Node;
import io.quarkus.runtime.annotations.Recorder;

import java.util.List;
import java.util.function.Supplier;

@Recorder
public class AlicerceRecorder {

    public Supplier<MessagesProvider> supplierMessagesProvider(List<String> languages, List<String> baseNames) {
        return () -> MessagesProvider.of(languages, baseNames);
    }

    public Supplier<Paths> supplierPaths(List<String[]> pathsArray) {
        return () -> new Paths(Node.of(pathsArray));
    }

    public Supplier<Menu> supplierMenu(List<String[]> pathsArray) {
        return () -> Menu.of(Node.of(pathsArray));
    }
}
