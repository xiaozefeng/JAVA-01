package io.github.mickey.concurrency.wait;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Way9 {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int[] arr = {1241, 32634, 75468, 58666};
        final CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> Calc.sum(arr));
        System.out.println(cf.get());
    }
}
