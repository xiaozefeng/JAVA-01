package io.github.mickey.concurrency.wait.concurrency.waitnotify;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Account {

    private int balance;

    public Account(int balance) {
        this.balance = balance;
    }

    public void transfer(Account target, int amt) {
        if (this.balance > 0) {
            this.balance -= amt;
            target.balance += amt;
        }
    }

    @Override
    public String toString() {
        return "balance:" + balance;
    }

    public static void main(String[] args) throws InterruptedException {
        Account a = new Account(200);
        Account b = new Account(100);
        Account c = new Account(100);

        CountDownLatch cdl = new CountDownLatch(3);

        ExecutorService executor = Executors.newFixedThreadPool(8);
        executor.submit(() -> {a.transfer(b, 100) ;
            cdl.countDown();});
        executor.submit(() -> {a.transfer(c, 100) ;
            cdl.countDown();});
        executor.submit(() -> {b.transfer(c, 100) ;
            cdl.countDown();});

        cdl.await();
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);

        executor.shutdown();
    }
}
