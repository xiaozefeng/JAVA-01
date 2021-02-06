package io.github.mickey.concurrency.wait.concurrency.sync;

public class Counter {

    private int num;

    public void increase(){
        this.num += 1;
    }

    public void decrease() {
        this.num -= 1;
    }

    public int getNum() {
        return num;
    }


    public static void main(String[] args) throws InterruptedException {
        int count = 10000;
        Counter c = new Counter();

        for (int i = 0; i < count; i++) {
            c.increase();
        }

        System.out.println("Single Thead Result:" + c.getNum());

        Counter c2 = new Counter();
        Thread t1 = new Thread(() -> {
            for (int i = 0; i < count / 2; i++) {
                c2.increase();
            }
        });



        Thread t2 = new Thread(() -> {
            for (int i = 0; i < count / 2; i++) {
                c2.increase();
            }
        });

        t1.start();
        t2.start();

        t1.join();
        t2.join();
        System.out.println("MultiThead Result:" + c2.getNum());
    }

}
