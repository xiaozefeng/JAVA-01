package io.github.mickey.concurrency.wait.concurrency.reconciliation;

import java.util.concurrent.atomic.AtomicReference;

public class Way1 {

    public static void main(String[] args) throws InterruptedException {
        AtomicReference<Order> order = new AtomicReference<>();
        AtomicReference<DeliveryOrder> deliveryOrder = new AtomicReference<>();
        Thread t1 = new Thread(() -> order.set(Order.getOrder(1)), "t1");
        Thread t2 = new Thread(() -> deliveryOrder.set(DeliveryOrder.getDeliveryOrder(1)), "t2");

        t1.start();
        t2.start();

        t1.join();
        t2.join();

        int diff = Differ.checkDiff(order.get(), deliveryOrder.get());
        Differ.saveDiff(diff);
    }


}
