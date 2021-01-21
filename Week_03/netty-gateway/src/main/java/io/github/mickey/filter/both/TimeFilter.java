package io.github.mickey.filter.both;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TimeFilter implements PreAndPostFilter {
//    private static final AtomicLong start = new AtomicLong();

    @Override
    public void preFilter(HttpRequest request, ChannelHandlerContext ctx) {
//        final long s = System.currentTimeMillis();
//        start.set(s);
//        log.info("request start:{}", start.get());
    }


    @Override
    public void postFilter(HttpResponse response, ChannelHandlerContext ctx) {
//        log.info("response start:{}", start.get());
//        final long end = System.currentTimeMillis();
//        log.info("response end:{}", end);
//        log.info("proxy request time:{} ms", end - start.get());
//        start.set(0);
    }

}
