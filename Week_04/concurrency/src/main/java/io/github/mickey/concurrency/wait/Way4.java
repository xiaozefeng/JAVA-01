package io.github.mickey.concurrency.wait;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Way4 {
    public static void main(String[] args) throws InterruptedException {

        int[] arr = {1241, 32634, 75468, 58666};
        AtomicInteger result = new AtomicInteger();
        final Thread t1 = new Thread(() -> {
            result.set(Calc.sum(arr));
        }, "t1");

        t1.start();

        final Lock l = new ReentrantLock();
        final Condition condition = l.newCondition();

        try {
            l.lock();
            while (t1.isAlive()) condition.await();

            condition.signalAll();
        }finally {
            l.unlock();
        }

        System.out.println(result.get());
    }
}
