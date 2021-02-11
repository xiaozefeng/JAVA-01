package io.github.mickey.filter.pre;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class HTTPRequestHeaderFilter implements PreFilter {

    @Override
    public void preFilter(HttpRequest request, ChannelHandlerContext ctx) {
        //final HttpHeaders headers = request.headers();
        //final Iterator<Map.Entry<String, String>> entryIterator = headers.iteratorAsString();
        //if (entryIterator.hasNext()) {
        //    log.info("http request headers:");
        //    entryIterator.forEachRemaining(e -> {
        //        log.info("{}: {}", e.getKey(), e.getValue());
        //    });
        //}
    }
}
