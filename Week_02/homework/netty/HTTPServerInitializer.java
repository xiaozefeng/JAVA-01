package io.netty.mickey.http;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpServerExpectContinueHandler;
import io.netty.handler.ssl.SslContext;

public class HTTPServerInitializer extends ChannelInitializer<SocketChannel> {
    private SslContext sslCtx;

    public HTTPServerInitializer(SslContext sslCtx) {
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(SocketChannel sc) throws Exception {
        ChannelPipeline p = sc.pipeline();
        if (sslCtx != null)
            p.addLast(sslCtx.newHandler(sc.alloc()));

        p.addLast(new HttpServerCodec());
        p.addLast(new HttpServerExpectContinueHandler());
        p.addLast(new HTTPServerHandler());
    }
}
