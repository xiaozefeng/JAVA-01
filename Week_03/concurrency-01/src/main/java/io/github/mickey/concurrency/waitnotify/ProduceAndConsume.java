package io.github.mickey.concurrency.waitnotify;


public class ProduceAndConsume {

    public static void main(String[] args) {
        Task t = new Task();

        new Thread(() -> {
            try {
                t.produce();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t1").start();


        new Thread(() -> {
            try {
                t.consume();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }, "t2").start();
    }


    private static class Task {
        private static final int max = 20;

        private int count;


        public void produce() throws InterruptedException {
            synchronized (this) {
                while (true) {
                    Thread.sleep(100);
                    System.out.println(Thread.currentThread().getName() + " running ..." + count);
                    if (count >= max) {
                        System.out.println("it's enough!!!");
                        this.wait();
                    } else {
                        count++;
                    }
                    notifyAll();
                }
            }
        }

        public void consume() throws InterruptedException {
            synchronized (this) {
                while (true) {
                    System.out.println(Thread.currentThread().getName() + " running.. " + count);
                    if (count <= 0) {
                        System.out.println("no product can consume");
                        this.wait();
                    } else {
                        count--;
                    }
                    notifyAll();
                }
            }
        }
    }

}
