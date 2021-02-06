package io.github.mickey.concurrency.wait.concurrency.reconciliation;

public class DeliveryOrder {
    @Override
    public String toString() {
        return "DeliveryOrder, id=" + id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int id;

    public DeliveryOrder(int id) {
        this.id = id;
    }

    public static DeliveryOrder getDeliveryOrder(int id) {
        try {
            System.out.println(Thread.currentThread().getName() + " get delivery order");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new DeliveryOrder(id);
    }
}
