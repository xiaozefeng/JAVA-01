package io.github.mickey.concurrency.wait;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Way6 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {

        final ExecutorService es = Executors.newSingleThreadExecutor();
        int[] arr = {1241, 32634, 75468, 58666};
        final Future<?> f = es.submit(() -> Calc.sum(arr));
        System.out.println(f.get());
        es.shutdown();
    }
}
