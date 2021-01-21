package io.github.mickey.okhttp;

public class Main {

    public static void main(String[] args)throws Exception {
        final long start = System.currentTimeMillis();
        Thread.sleep(500l);
        final long end= System.currentTimeMillis();

        System.out.println(end-start);
    }
}
