package io.github.mickey.concurrency.wait;

import java.util.concurrent.atomic.AtomicInteger;

public class Way3 {
    public static void main(String[] args) throws InterruptedException {

        int[] arr = {1241, 32634, 75468, 58666};
        final AtomicInteger result = new AtomicInteger();
        final Thread t1 = new Thread(() -> {
            result.set(Calc.sum(arr));
        }, "t1");

        t1.start();

        synchronized (t1){
            while (t1.isAlive()){
                t1.wait();
            }
        }
        System.out.println(result.get());
    }
}
