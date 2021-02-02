package io.github.mickey.concurrency.wait;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class Way5 {
    public static void main(String[] args) throws InterruptedException {

        final CountDownLatch cdl = new CountDownLatch(1);

        int[] arr = {1241, 32634, 75468, 58666};
        final AtomicInteger result = new AtomicInteger();
        final Thread t1 = new Thread(() -> {
            result.set(Calc.sum(arr));
            cdl.countDown();
        }, "t1");

        t1.start();

        cdl.await();
        System.out.println(result.get());
    }
}
