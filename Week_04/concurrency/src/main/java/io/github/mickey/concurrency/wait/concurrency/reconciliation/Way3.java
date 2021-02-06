package io.github.mickey.concurrency.wait.concurrency.reconciliation;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicReference;

public class Way3 {

    public static void main(String[] args) throws InterruptedException {
        AtomicReference<Order> order = new AtomicReference<>();
        AtomicReference<DeliveryOrder> deliveryOrder = new AtomicReference<>();
        ExecutorService es = Executors.newFixedThreadPool(4);
        CountDownLatch cd = new CountDownLatch(2);

        es.execute(() -> {
            order.set(Order.getOrder(1));
            cd.countDown();
        });
        es.execute(() ->{
            deliveryOrder.set(DeliveryOrder.getDeliveryOrder(1));
            cd.countDown();
        });

        cd.await();
        int diff = Differ.checkDiff(order.get(), deliveryOrder.get());
        Differ.saveDiff(diff);
        es.shutdown();
    }


}
