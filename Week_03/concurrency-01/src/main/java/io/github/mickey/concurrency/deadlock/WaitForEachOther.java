package io.github.mickey.concurrency.deadlock;

public class WaitForEachOther {
    private static final Object lock1 = new Object();

    private static final Object lock2 = new Object();

    public static void main(String[] args) {
        new Thread(new Task1(), "t1").start();
        new Thread(new Task2(), "t2").start();
    }

    private static class Task1 implements Runnable{

        @Override
        public void run() {
            synchronized (lock1) {
                System.out.println(Thread.currentThread().getName() +" holding lock1");
                try { Thread.sleep(100); } catch (InterruptedException e) { }
                System.out.println(Thread.currentThread().getName() + " waiting for lock2");
                synchronized (lock2) {
                    System.out.println(Thread.currentThread().getName() + "holding lock2");
                }
            }
        }
    }

    private static class Task2 implements Runnable{

        @Override
        public void run() {
            synchronized (lock2) {
                System.out.println(Thread.currentThread().getName() +" holding lock2");
                try { Thread.sleep(100); } catch (InterruptedException e) { }
                System.out.println(Thread.currentThread().getName() + " waiting for lock1");
                synchronized (lock1) {
                    System.out.println(Thread.currentThread().getName() + "holding lock1");
                }
            }
        }
    }
}
