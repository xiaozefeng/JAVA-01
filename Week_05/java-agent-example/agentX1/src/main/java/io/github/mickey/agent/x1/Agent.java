package io.github.mickey.agent.x1;

import java.lang.instrument.Instrumentation;

/**
 * @author mickey
 * @date 2/16/21 22:13
 */
public class Agent {

    public static void premain(String arg, Instrumentation instrumentation) {
        System.out.println("this is an agent.");
        System.out.println("agent arg:" + arg);
    }
}
