package io.github.mickey.agent.x3.goal;

/**
 * @author mickey
 * @date 2/16/21 23:17
 */
public class TargetX3 {

    public void foo() throws InterruptedException {
        System.out.println("foo");
        Thread.sleep(200);
    }

    public void bar() throws InterruptedException {
        System.out.println("bar");
        Thread.sleep(300);
    }

    public static void main(String[] args) throws InterruptedException {
        final TargetX3 t = new TargetX3();
        t.bar();
        t.foo();
    }



}
