package io.github.mickey.concurrency.wait.concurrency.sync;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.function.Function;

public class ObjPool<T, R> {

    private final Semaphore semaphore;
    private final List<T> pool;

    public ObjPool(int size, T[] t) {
        this.semaphore = new Semaphore(size);
        pool = new LinkedList<>();
        pool.addAll(Arrays.asList(t));
    }

    public R exec(Function<T, R> f) {
        T t = null;
        try {
            semaphore.acquire();
            t = pool.remove(0);
            return f.apply(t);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            pool.add(t);
            semaphore.release();
        }
        return null;
    }

}
