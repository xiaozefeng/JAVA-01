package io.github.mickey.agent.x2;

/**
 * @author mickey
 * @date 2/16/21 22:49
 */
public class TargetX2 {

    public void foo() {
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("this is foo.");
    }


    public void bar() {
        try {
            Thread.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("this is bar.");
    }

    public static void main(String[] args) {
        final TargetX2 t = new TargetX2();
        t.bar();
        t.foo();
    }
}
