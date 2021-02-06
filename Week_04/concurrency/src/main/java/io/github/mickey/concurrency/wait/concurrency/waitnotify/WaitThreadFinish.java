package io.github.mickey.concurrency.wait.concurrency.waitnotify;

public class WaitThreadFinish {
    /**
     * three way to wait thread to finish the logic.
     * 1. don't set daemon thread
     * 2. caller thead sleep some millis
     * 3. call target thread' join() , example:  t.join()
     *
     * @param args
     * @throws InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(new Task(), "t1");
        // 2. don't set daemon thread (false)
        t.setDaemon(true);
        t.start();
        System.out.println("main thread running.");

        // 1. call thread sleep some millis
        // Thread.sleep(1000);

        // 3. call thread.join()
        t.join();
    }


    private static class Task implements Runnable {
        @Override
        public void run() {
            System.out.println(Thread.currentThread().getName() + " running. ");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
