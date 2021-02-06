package io.github.mickey.concurrency.wait.concurrency.reconciliation;

public class Differ {

    public static int checkDiff(Order order, DeliveryOrder deliveryOrder) {
        try {
            System.out.println(Thread.currentThread().getName() + " check diff , order =" + order.getId() +
                    " delivery order=" + deliveryOrder.getId());
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }

    public static void saveDiff(int diff) {
        System.out.println(Thread.currentThread().getName() + " save the diff");
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

}
