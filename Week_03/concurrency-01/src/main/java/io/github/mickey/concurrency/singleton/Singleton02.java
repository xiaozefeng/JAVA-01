package io.github.mickey.concurrency.singleton;

public class Singleton02 {
    private static volatile Singleton02 INSTANCE;

    private Singleton02(){}

    public Singleton02 letInstance(){
        if (INSTANCE == null){
            synchronized (Singleton02.class){
                if (INSTANCE == null)
                    INSTANCE = new Singleton02();
            }
        }
        return INSTANCE;
    }
}
