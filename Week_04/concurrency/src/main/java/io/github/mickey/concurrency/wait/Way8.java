package io.github.mickey.concurrency.wait;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class Way8 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int[] arr = {1241, 32634, 75468, 58666};
        final FutureTask<Integer> ft = new FutureTask<>(() -> Calc.sum(arr));
        final ExecutorService es = Executors.newSingleThreadExecutor();
        es.submit(ft);
        System.out.println(ft.get());
        es.shutdown();
    }
}
