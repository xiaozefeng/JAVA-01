package io.github.mickey.concurrency.wait.concurrency.reconciliation;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Way2 {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(4);

        Future<Order> f1 = es.submit(() -> Order.getOrder(1));
        Future<DeliveryOrder> f2 = es.submit(() -> DeliveryOrder.getDeliveryOrder(1));


        int diff = Differ.checkDiff(f1.get(), f2.get());
        Differ.saveDiff(diff);

        es.shutdown();
    }
}
