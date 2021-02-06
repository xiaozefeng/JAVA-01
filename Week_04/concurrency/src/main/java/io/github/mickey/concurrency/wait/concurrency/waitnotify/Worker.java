package io.github.mickey.concurrency.wait.concurrency.waitnotify;


public class Worker {

    public static void main(String[] args) throws InterruptedException {
        Worker worker = new Worker();
        Thread t1 = new Thread(worker::consume, "t1");
        Thread t2 = new Thread(worker::produce, "t2");

        t1.start();
        t2.start();
    }

    private int amount;

    public void produce(){
        synchronized (this) {

            while (true){
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(amount>200){
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " 生产 amount:" + amount);
                this.amount++;
                notifyAll();
            }
        }
    }

    public void consume(){
        synchronized (this){
            while (true){
                if(this.amount <=0) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println(Thread.currentThread().getName() + " 消费amount:" + amount);
                this.amount--;

                notifyAll();
            }
        }
    }
}
