package io.github.mickey.config;

import io.github.mickey.executor.NamedThreadFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * @author mickey
 * @date 2/11/21 15:18
 */
@ComponentScan("io.github.mickey")
@Configuration
@Slf4j
public class SpringConfig {

    @Bean("proxyTaskPool")
    public ThreadPoolExecutor proxyTask() {
        int cores = Runtime.getRuntime().availableProcessors() * 2;
        long keepAliveTime = 1000L;
        int queueSize = 2048;
        RejectedExecutionHandler rejectedExecutionHandler = new ThreadPoolExecutor.CallerRunsPolicy();
        ThreadPoolExecutor executor= new ThreadPoolExecutor(cores, cores, keepAliveTime, TimeUnit.MILLISECONDS,
                new ArrayBlockingQueue<>(queueSize), new NamedThreadFactory("proxyService"),
                rejectedExecutionHandler);
        log.info("proxy service executor init success");
        return executor;
    }
}
