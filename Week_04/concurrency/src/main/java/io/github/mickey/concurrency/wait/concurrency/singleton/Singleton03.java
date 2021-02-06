package io.github.mickey.concurrency.wait.concurrency.singleton;

public class Singleton03 {
    private Singleton03() { }

    private static class SingletonHolder {
        private static final Singleton03 INSTANCE = new Singleton03();
    }

    public Singleton03 getInstance() {
        return SingletonHolder.INSTANCE;
    }
}
