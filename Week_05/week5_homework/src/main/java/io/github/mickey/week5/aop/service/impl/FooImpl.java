package io.github.mickey.week5.aop.service.impl;

import io.github.mickey.week5.aop.service.Foo;

/**
 * @author mickey
 * @date 2/8/21 21:27
 */
public class FooImpl implements Foo {
    @Override
    public void doSomething() {
        System.out.println("foo do something...");
    }
}
