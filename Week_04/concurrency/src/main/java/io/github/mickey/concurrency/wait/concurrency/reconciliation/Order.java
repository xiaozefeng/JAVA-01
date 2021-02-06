package io.github.mickey.concurrency.wait.concurrency.reconciliation;

public class Order {
    private int id;

    @Override
    public String toString() {
        return "Order, id=" + id;
    }

    public Order(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public static Order getOrder(int id) {
        try {
            System.out.println(Thread.currentThread().getName() + " get  order");
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return new Order(id);
    }
}
