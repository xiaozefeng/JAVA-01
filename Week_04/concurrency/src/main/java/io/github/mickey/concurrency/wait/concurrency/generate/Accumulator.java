package io.github.mickey.concurrency.wait.concurrency.generate;

import java.util.Arrays;

public class Accumulator {

    public static void main(String[] args) {
        String source = "45\n" +
                "2\n" +
                "14\n" +
                "95\n" +
                "51\n" +
                "5\n" +
                "15\n" +
                "5\n" +
                "9\n" +
                "16\n" +
                "7\n" +
                "5\n" +
                "17\n";
        String[] numbers= source.split("\n");
        int result = Arrays.stream(numbers).mapToInt((Integer::parseInt)).sum();
        // doorway: 106
        // unionbuy: 50
        // 287 + 106 + 50
        System.out.println(result); // 443
    }
}
