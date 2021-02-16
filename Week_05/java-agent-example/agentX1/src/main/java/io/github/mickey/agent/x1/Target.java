package io.github.mickey.agent.x1;

/**
 * @author mickey
 * @date 2/16/21 22:28
 */
public class Target {
    /**
     * java -javaagent:agent.jar=abc Target
     * @param args
     */
    public static void main(String[] args) {
        System.out.println("hello world");
    }
}
