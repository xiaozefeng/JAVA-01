package io.github.mickey.netty;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;

public class HTTPServerHandler extends SimpleChannelInboundHandler<HttpObject> {

    private static final byte[] CONTENT = {'h', 'e', 'l', 'l', 'o', ',', 'n', 'i', 'o'};

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpRequest) {
            HttpRequest req = (HttpRequest) msg;
            boolean keepAlive = HttpUtil.isKeepAlive(req);
            DefaultFullHttpResponse res = new DefaultFullHttpResponse(req.protocolVersion(), HttpResponseStatus.OK,
                    Unpooled.wrappedBuffer(CONTENT));

            res.headers()
                    .set(HttpHeaderNames.CONTENT_TYPE, HttpHeaderValues.TEXT_PLAIN)
                    .setInt(HttpHeaderNames.CONTENT_LENGTH, res.content().readableBytes());
            if (keepAlive) {
                if (!res.protocolVersion().isKeepAliveDefault())
                    res.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
            } else
                res.headers().set(HttpHeaderNames.CONNECTION, HttpHeaderValues.CLOSE);


            ChannelFuture f = ctx.write(res);
            if (!keepAlive)
                f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
