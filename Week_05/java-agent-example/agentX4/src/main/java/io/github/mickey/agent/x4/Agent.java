package io.github.mickey.agent.x4;

import java.lang.instrument.Instrumentation;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author mickey
 * @date 2/16/21 23:35
 */
public class Agent {

    public static void premain(String arg, Instrumentation inst) {

        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(() -> {
            Metric.printMemoryInfo();
            Metric.printGCInfo();
        }, 0, 3, TimeUnit.SECONDS);
    }
}
