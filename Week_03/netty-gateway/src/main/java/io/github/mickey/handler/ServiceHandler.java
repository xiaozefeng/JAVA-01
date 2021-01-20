package io.github.mickey.handler;

import java.util.function.Consumer;

public interface ServiceHandler {
    byte[] handle (String in, Consumer<byte[]> callback);
}
