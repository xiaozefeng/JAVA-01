package io.github.mickey.router;

import io.github.mickey.handler.ServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.function.Consumer;

@Component
public class RandomHTTPEndpointRouter implements HTTPEndpointRouter {

    @Autowired
    private  ServiceHandler serviceHandler;


    @Override
    public void route(List<String> endpoints, Consumer<byte[]> callback) {
        int len = endpoints.size();
        Random r = new Random();
        final int index = r.nextInt(len);
        serviceHandler.handle(endpoints.get(index), callback);
    }
}
