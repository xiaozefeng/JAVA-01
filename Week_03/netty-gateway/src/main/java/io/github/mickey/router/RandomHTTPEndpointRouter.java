package io.github.mickey.router;

import io.github.mickey.context.ContextHolder;
import io.github.mickey.handler.ServiceHandler;
import io.netty.buffer.Unpooled;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.HttpResponseStatus;
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
        byte[] bytes = serviceHandler.handle(endpoints.get(index));
        setResponse(bytes);
        return bytes;
    }
    private void setResponse(byte[] bytes) {
        DefaultFullHttpResponse resp = new DefaultFullHttpResponse(ContextHolder.get().getReq().protocolVersion(), HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(bytes));
        resp.headers().set("hello", "netty");
        ContextHolder.get().setResp(resp);
    }
}
