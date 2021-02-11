package io.github.mickey.router;

import java.util.List;

public interface HTTPEndpointRouter {

    byte[] route(List<String> endpoints);
}
