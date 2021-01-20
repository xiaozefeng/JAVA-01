package io.github.mickey.server;

import io.github.mickey.config.ServiceConfig;
import io.github.mickey.executor.ProxyServiceExecutor;
import io.github.mickey.handler.ServiceHandler;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;


@Slf4j
public class BootstrapServerHandler extends ChannelInboundHandlerAdapter {

    private final ServiceHandler serviceHandler;
    private final String serviceId;

    public BootstrapServerHandler(ServiceHandler serviceHandler, String serviceId) {
        this.serviceHandler = serviceHandler;
        this.serviceId = serviceId;
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            FullHttpRequest request = (FullHttpRequest) msg;
            ProxyServiceExecutor.getExecutor().submit(() -> {
                String url = processRequest(request);
                requestService(url, ctx, request);
            });
        } finally {
            ReferenceCountUtil.release(msg);
        }

    }

    private void processResponse(ChannelHandlerContext ctx, FullHttpRequest request, byte[] content) {
        log.info("process response");
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
        } else {
            // Tell the client we're going to close the connection.
            response.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);
        }

        final ChannelFuture f = ctx.write(response);
        if (!keepAlive)
            f.addListener(ChannelFutureListener.CLOSE);

        ctx.flush();
    }

    private void requestService(String url, ChannelHandlerContext ctx, FullHttpRequest request) {
        serviceHandler.handle(url, content -> processResponse(ctx, request, content));
    }

    private String processRequest(FullHttpRequest request) {
        final String backendUrl = ServiceConfig.get(serviceId);
        log.info("backend url:${}, request uri:${}", backendUrl, request.uri());
        return backendUrl + request.uri();
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
