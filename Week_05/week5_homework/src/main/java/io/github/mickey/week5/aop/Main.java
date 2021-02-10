package io.github.mickey.week5.aop;

import io.github.mickey.week5.aop.service.Bar;
import io.github.mickey.week5.aop.service.Foo;

/**
 * @author mickey
 * @date 2/8/21 21:31
 */
public class Main {
    public static void main(String[] args) {
        AppContext context= new AppContext();

        Foo foo = context.getBean(Foo.class);
        foo.doSomething();

        System.out.println();

        Bar bar = context.getBean(Bar.class);
        bar.bar();
    }
}
