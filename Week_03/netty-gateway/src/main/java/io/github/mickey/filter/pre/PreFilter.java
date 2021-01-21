package io.github.mickey.filter.pre;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;

public interface PreFilter {

    void preFilter(HttpRequest request, ChannelHandlerContext ctx);
}
