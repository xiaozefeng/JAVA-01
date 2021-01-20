package io.github.mickey.server;


import io.github.mickey.Application;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BootstrapServer {

    private final Logger logger = LoggerFactory.getLogger(Application.class);
    private final int port;

    public BootstrapServer(int port) {
        this.port = port;
    }


    public void start() throws Exception{
        NioEventLoopGroup boos = new NioEventLoopGroup(1);
        NioEventLoopGroup work = new NioEventLoopGroup(16);

        ServerBootstrap b = new ServerBootstrap();
        b.option(ChannelOption.SO_BACKLOG, 128)
                .option(ChannelOption.TCP_NODELAY, true)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.SO_REUSEADDR, true)
                .option(ChannelOption.SO_RCVBUF, 32 * 1024)
                .option(ChannelOption.SO_SNDBUF, 32 * 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(EpollChannelOption.SO_REUSEPORT, true)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

        try {


            b.group(boos, work)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new BootstrapServerInitializer());

            final Channel c = b.bind(port).sync().channel();
            logger.info("start netty server at "  + port);
            c.closeFuture().sync();
        }finally {
            boos.shutdownGracefully();
            work.shutdownGracefully();
        }
    }
}
