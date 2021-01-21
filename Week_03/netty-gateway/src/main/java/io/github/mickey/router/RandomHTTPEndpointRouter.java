package io.github.mickey.router;

import io.github.mickey.handler.ServiceHandler;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

public class RandomHTTPEndpointRouter implements HTTPEndpointRouter {
    private final ServiceHandler serviceHandler;

    public RandomHTTPEndpointRouter(ServiceHandler serviceHandler) {
        this.serviceHandler = serviceHandler;
    }

    @Override
    public void route(List<String> endpoints, Consumer<byte[]> callback) {
        int len = endpoints.size();
        Random r = new Random();
        final int index = r.nextInt(len);
        serviceHandler.handle(endpoints.get(index), callback);
    }
}
