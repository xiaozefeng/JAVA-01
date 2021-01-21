package io.github.mickey.filter.post;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpResponse;

public interface PostFilter {

    void postFilter(HttpResponse response, ChannelHandlerContext ctx);
}
