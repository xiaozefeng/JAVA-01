package io.github.mickey.executor;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Slf4j
public class ProxyServiceExecutor {


    private static final ThreadPoolExecutor EXECUTOR;

    static {
        int cores = Runtime.getRuntime().availableProcessors() * 2;
        long keepAliveTime = 1000L;
        int queueSize = 2048;
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        EXECUTOR = new ThreadPoolExecutor(cores, cores, keepAliveTime, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(queueSize), new NamedThreadFactory("proxyService"),
                rejectedExecutionHandler);
        log.info("proxy service executor init success");
    }

    public static ThreadPoolExecutor getExecutor() {
        return EXECUTOR;
    }

}
