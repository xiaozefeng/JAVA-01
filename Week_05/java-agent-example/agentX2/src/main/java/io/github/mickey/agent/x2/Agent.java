package io.github.mickey.agent.x2;

import io.github.mickey.agent.x2.transformer.PerformMonitorTransformer;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;

/**
 * @author mickey
 * @date 2/16/21 22:38
 */
public class Agent {
    public static void premain(String arg, Instrumentation inst) {
        ClassFileTransformer cft = new PerformMonitorTransformer();
        inst.addTransformer(cft);
    }
}
