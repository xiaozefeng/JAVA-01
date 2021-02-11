package io.github.mickey.server;

import io.github.mickey.config.ServiceConfigLoader;
import io.github.mickey.executor.ProxyServiceExecutor;
import io.github.mickey.filter.both.PreAndPostFilter;
import io.github.mickey.filter.both.TimeFilter;
import io.github.mickey.filter.post.HTTPResponseHeaderFilter;
import io.github.mickey.filter.post.PostFilter;
import io.github.mickey.filter.pre.HTTPRequestHeaderFilter;
import io.github.mickey.filter.pre.PreFilter;
import io.github.mickey.handler.OkHTTPServiceHandler;
import io.github.mickey.router.HTTPEndpointRouter;
import io.github.mickey.router.RandomHTTPEndpointRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.ReferenceCountUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Slf4j
public class ServerHandler extends ChannelInboundHandlerAdapter {

    private List<PreFilter> preFilters;
    private List<PostFilter> postFilters;
    private List<PreAndPostFilter> preAndPostFilters;
    private HTTPEndpointRouter router;


    public ServerHandler() {
        init();
    }

    private void init() {
        this.preFilters = loadPreFilters();
        this.postFilters = loadPostFilters();
        this.preAndPostFilters = loadPreAndPostFilters();
        this.router = initRouter();
    }

    private HTTPEndpointRouter initRouter() {
        return new RandomHTTPEndpointRouter(new OkHTTPServiceHandler());
    }

    private List<PreAndPostFilter> loadPreAndPostFilters() {
        List<PreAndPostFilter> filters = new ArrayList<>();
        filters.add(new TimeFilter());
        return filters;
    }

    private List<PostFilter> loadPostFilters() {
        List<PostFilter> filters = new ArrayList<>();
        filters.add(new HTTPResponseHeaderFilter());
        return filters;
    }

    private List<PreFilter> loadPreFilters() {
        List<PreFilter> filters = new ArrayList<>();
        filters.add(new HTTPRequestHeaderFilter());
        return filters;
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        FullHttpRequest request = (FullHttpRequest) msg;
        try {
            ProxyServiceExecutor.getExecutor().execute(() -> {
                final List<String> endpoints = getEndpoints(request);
                doPreFilters(preFilters, preAndPostFilters, request, ctx);
                router.route(endpoints, bytes -> {
                    doPostFilters(postFilters, preAndPostFilters, bytes, request, ctx);
                });
            });

        } catch (Exception e) {
            log.error(e.getMessage(),e);
        } finally {
            ReferenceCountUtil.release(msg);
        }


    }

    private void doPostFilters(List<PostFilter> postFilters, List<PreAndPostFilter> preAndPostFilters, byte[] result, FullHttpRequest request, ChannelHandlerContext ctx) {
        FullHttpResponse response = new DefaultFullHttpResponse(request.protocolVersion(), HttpResponseStatus.OK,
                Unpooled.wrappedBuffer(result));

        for (PostFilter postFilter : postFilters)
            postFilter.postFilter(response, ctx);

        for (PreAndPostFilter filter : preAndPostFilters)
            filter.postFilter(response, ctx);


        writeAndFlush(ctx, request, response);
    }

    private void writeAndFlush(ChannelHandlerContext ctx, FullHttpRequest request, FullHttpResponse response) {
        processResponse(ctx, request, response.content().array());
    }


    private void doPreFilters(List<PreFilter> preFilters, List<PreAndPostFilter> preAndPostFilters, FullHttpRequest request, ChannelHandlerContext ctx) {
        for (PreFilter preFilter : preFilters)
            preFilter.preFilter(request, ctx);

        for (PreAndPostFilter filter : preAndPostFilters)
            filter.preFilter(request, ctx);

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
