package io.github.mickey.week5.aop.service.impl;

import io.github.mickey.week5.aop.service.Bar;

/**
 * @author mickey
 * @date 2/11/21 00:47
 */
public class BarImpl implements Bar {
    @Override
    public void bar() {
        System.out.println("bar bar bar ....");
    }
}
