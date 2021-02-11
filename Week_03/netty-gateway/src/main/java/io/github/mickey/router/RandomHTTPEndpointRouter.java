package io.github.mickey.router;

import io.github.mickey.handler.ServiceHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

@Component
public class RandomHTTPEndpointRouter implements HTTPEndpointRouter {

    @Autowired
    private  ServiceHandler serviceHandler;


    @Override
    public byte[] route(List<String> endpoints) {
        int len = endpoints.size();
        Random r = new Random();
        final int index = r.nextInt(len);
        return serviceHandler.handle(endpoints.get(index));
    }
}
