package io.github.mickey.concurrency.wait;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class Way7 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int[] arr = {1241, 32634, 75468, 58666};
        final FutureTask<Integer> ft = new FutureTask<>(() -> Calc.sum(arr));
        new Thread(ft).start();
        System.out.println(ft.get());
    }
}
