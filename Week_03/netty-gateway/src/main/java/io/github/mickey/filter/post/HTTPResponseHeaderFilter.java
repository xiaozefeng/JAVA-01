package io.github.mickey.filter.post;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class HTTPResponseHeaderFilter implements PostFilter{

    @Override
    public void postFilter(HttpResponse response, ChannelHandlerContext ctx) {
//        final HttpHeaders headers = response.headers();
//        final Iterator<Map.Entry<String, String>> entryIterator = headers.iteratorAsString();
//        if (entryIterator.hasNext()){
//            log.info("http response headers");
//            entryIterator.forEachRemaining(e ->{
//                log.info("{}: {}", e.getKey(), e.getValue());
//            });
//        }
    }
}
