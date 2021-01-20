package io.github.mickey.executor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ProxyServiceExecutor {

    private final static Logger logger = LoggerFactory.getLogger(ProxyServiceExecutor.class);

    private static final ThreadPoolExecutor EXECUTOR;

    static {
        int cores = Runtime.getRuntime().availableProcessors() * 2;
        long keepAliveTime = 1000L;
        int queueSize = 2048;
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        EXECUTOR = new ThreadPoolExecutor(cores, cores, keepAliveTime, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(queueSize), new NamedThreadFactory("proxyService"),
                rejectedExecutionHandler);
        logger.info("proxy service executor init success");
    }

    public static ThreadPoolExecutor getExecutor() {
        return EXECUTOR;
    }

}
