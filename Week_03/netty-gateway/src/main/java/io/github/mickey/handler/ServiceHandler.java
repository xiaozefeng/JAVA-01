package io.github.mickey.handler;

import java.util.function.Consumer;

public interface ServiceHandler {
    void handle (String in, Consumer<byte[]> callback);
}
