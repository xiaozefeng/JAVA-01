package io.github.mickey.concurrency.wait;

public class Calc {

    private Calc(){}

    public static int sum(int[] arr) {
        int sum = 0;
        for (int num : arr) {
            sum += num;
        }
        return sum;
    }
}
