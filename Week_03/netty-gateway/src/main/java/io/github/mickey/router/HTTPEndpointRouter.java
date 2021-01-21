package io.github.mickey.router;

import java.util.List;
import java.util.function.Consumer;

public interface HTTPEndpointRouter {

    void route(List<String> endpoints, Consumer<byte[]> callback);
}
