package io.github.mickey.concurrency;

public class WaitRun {

    public static void main(String[] args) throws Exception{
        Thread t1 = new Thread(() -> {
            try {
                Thread.sleep(1000);
                System.out.println(Thread.currentThread().getName() + " executed");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }, "t1");
        t1.setDaemon(true);
        t1.start();
        // t1.join();

        System.out.println(Thread.currentThread().getName() + " executed");
        Thread.sleep(1001);
    }
}
