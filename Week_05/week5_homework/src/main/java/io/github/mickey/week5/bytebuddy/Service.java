package io.github.mickey.week5.bytebuddy;

import io.github.mickey.week5.bytebuddy.annotaion.Log;

/**
 * @author mickey
 * @date 2/15/21 23:34
 */
public class Service {

    @Log
    public int foo(int val) {
        System.out.println("service.foo(), val:" + val);
        return val;
    }


    public int bar(int val){
        System.out.println("service.bar(), val:" + val);
        return val;
    }
}
