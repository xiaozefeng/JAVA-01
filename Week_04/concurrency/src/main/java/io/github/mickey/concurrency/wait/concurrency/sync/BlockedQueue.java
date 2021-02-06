package io.github.mickey.concurrency.wait.concurrency.sync;




import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BlockedQueue {

    private static class Node {
        int val;
        Node next;

        public Node(int val, Node next) {
            this.val = val;
            this.next = next;
        }
    }

    private Node head;
    private int size;
    private final int capacity;

    public BlockedQueue(int capacity) {
        this.capacity = capacity;
        this.head = null;
        this.size = 0;
    }

    private final Lock l = new ReentrantLock();
    private final Condition notFull = l.newCondition();
    private final Condition notEmpty = l.newCondition();

    private boolean isEmpty() {
        return size == 0;
    }

    public boolean isFull() {
        return size == capacity;
    }

    public void enqueue(int val) {
        try {
            l.lock();
            while (isFull()) {
                notFull.await();
            }
            if (head == null) head = new Node(val, null);
            else head.next = new Node(val, null);
            size++;
            notEmpty.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            l.unlock();

        }
    }

    public int dequeue() {
        int re = 0;
        try {
            l.lock();
            while (isEmpty()) {
                notEmpty.await();
            }
            re =  head.val;
            head = head.next;
            size--;
            notFull.signalAll();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            l.unlock();
        }
        return re;
    }


}
