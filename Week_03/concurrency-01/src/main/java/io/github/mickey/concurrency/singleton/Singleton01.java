package io.github.mickey.concurrency.singleton;

// 饿汉
public class Singleton01 {
    private static final Singleton01 INSTANCE = new Singleton01();

    private Singleton01() { }

    public Singleton01 getInstance() { return INSTANCE;}
}