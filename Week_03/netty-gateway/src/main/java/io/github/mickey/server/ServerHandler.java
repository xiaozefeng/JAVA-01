package io.github.mickey.server;

import io.github.mickey.config.ServiceConfigLoader;
import io.github.mickey.context.Context;
import io.github.mickey.context.ContextHolder;
import io.github.mickey.router.HTTPEndpointRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.stream.Collectors;


@Slf4j
@Component
@ChannelHandler.Sharable
public class ServerHandler extends ChannelInboundHandlerAdapter {


    @Autowired
    private HTTPEndpointRouter router;

    @Autowired
    @Qualifier("proxyTaskPool")
    private ThreadPoolExecutor proxyTaskPool;

    public ServerHandler() {
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request = (FullHttpRequest) msg;
        try {
            Future<byte[]> f = proxyTaskPool.submit(() -> {
                ContextHolder.set(Context.of(ctx, request, null));
                final List<String> endpoints = getEndpoints(request);
                return router.route(endpoints);
            });
            byte[] bytes = f.get();
            writeAndFlush(ctx, request, bytes);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            ReferenceCountUtil.release(msg);
            ContextHolder.remove();
        }


    }



    private void writeAndFlush(ChannelHandlerContext ctx, FullHttpRequest request, byte[] bytes) {
        processResponse(ctx, request, bytes);
    }


    private void processResponse(ChannelHandlerContext ctx, FullHttpRequest request, byte[] content) {
        final boolean keepAlive = HttpUtil.isKeepAlive(request);
        FullHttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(content));

        response.headers()
                .set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.APPLICATION_JSON)
                .setInt(HttpHeaderNames.CONTENT_LENGTH, content.length);

        if (keepAlive) {
            if (!request.protocolVersion().isKeepAliveDefault()) {
                response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            }
        } else
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);


        final ChannelFuture f = ctx.write(response);
        if (!keepAlive)
            f.addListener(ChannelFutureListener.CLOSE);

        ctx.flush();
    }


    private List<String> getEndpoints(FullHttpRequest request) {
        String serviceId = getServiceId(request);
        List<String> urls = ServiceConfigLoader.get(serviceId);
        log.info("proxy urls:${}, request uri:${}", urls, request.uri());
        urls = urls.stream().map(e -> e.concat(request.uri()))
                .collect(Collectors.toList());
        return urls;
    }

    private String getServiceId(FullHttpRequest request) {
        String serviceId = request.headers().get("serviceId");
        if (serviceId == null || "".equals(serviceId))
            serviceId = "backend";
        return serviceId;
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
