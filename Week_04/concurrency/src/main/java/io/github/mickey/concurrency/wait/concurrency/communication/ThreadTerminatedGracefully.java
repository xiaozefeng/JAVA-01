package io.github.mickey.concurrency.wait.concurrency.communication;


public class ThreadTerminatedGracefully {

    public static void main(String[] args) throws InterruptedException {
        Task task = new Task();
        Thread t1 = new Thread(task, "t1");
        t1.start();

        System.out.println("main thread running...");
        Thread.sleep(1000);
        task.cancel();
    }


    private static class Task implements Runnable {
        private volatile boolean flag =true;

        private int count;

        public void cancel() {
            this.flag = false;
        }

        @Override
        public void run() {
            while (flag && !Thread.interrupted()) {
                System.out.println(Thread.currentThread().getName() +" running. " + (++count));
                try {
                    Thread.sleep(10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
