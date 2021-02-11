package io.github.mickey.context;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import lombok.Getter;
import lombok.Setter;

/**
 * @author mickey
 * @date 2/11/21 19:51
 */
@Getter
@Setter
public class Context {

    private ChannelHandlerContext chc;
    private FullHttpRequest req;
    private FullHttpResponse resp;

    public Context(ChannelHandlerContext chc, FullHttpRequest req, FullHttpResponse resp) {
        this.chc = chc;
        this.req = req;
        this.resp = resp;
    }

    public static Context of(ChannelHandlerContext chc, FullHttpRequest req, FullHttpResponse resp) {
        return new Context(chc, req, resp);
    }
}
