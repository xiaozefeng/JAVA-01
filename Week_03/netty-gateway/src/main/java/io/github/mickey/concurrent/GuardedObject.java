package io.github.mickey.concurrent;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Predicate;

/**
 * @author mickey
 * @date 2/11/21 18:23
 */
public class GuardedObject<T> {
    private T obj;
    private final Lock lock = new ReentrantLock();
    private final Condition done = lock.newCondition();
    private final int timeout;
    private final TimeUnit timeUnit;

    public GuardedObject(int timeout) {
        this.timeout = timeout;
        this.timeUnit = TimeUnit.SECONDS;
    }

    public GuardedObject(int timeout, TimeUnit timeUnit) {
        this.timeout = timeout;
        this.timeUnit = timeUnit;
    }

    public T get(Predicate<T> p) {
        try {
            lock.lock();
            while (!p.test(obj))
                done.await(timeout, timeUnit);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }
        return obj;
    }

    public void onDone(T obj) {
        lock.lock();
        try {
            this.obj = obj;
            done.signalAll();
        } finally {
            lock.unlock();
        }

    }

}
