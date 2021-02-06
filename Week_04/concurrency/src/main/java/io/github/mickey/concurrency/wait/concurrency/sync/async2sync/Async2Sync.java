package io.github.mickey.concurrency.wait.concurrency.sync.async2sync;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Async2Sync<T> {

    private final Lock l = new ReentrantLock();
    private final Condition done = l.newCondition();

    private T response;

    public T get(int timeout) {
        long start = System.currentTimeMillis();

        try {
            this.l.lock();
            while (!isDone()) {
                done.await(timeout, TimeUnit.MILLISECONDS);
                long cur = System.currentTimeMillis();
                if (isDone() || cur - start > timeout) {
                    break;
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            this.l.unlock();
        }

        if (!isDone()) {
            throw new RuntimeException("request timeout");
        }
        return response;
    }

    private boolean isDone() {
        return response != null;
    }

    public void receive(T response) {
        l.lock();
        try {
            this.response = response;
            done.signalAll();
        } finally {
            l.unlock();
        }
    }
}
