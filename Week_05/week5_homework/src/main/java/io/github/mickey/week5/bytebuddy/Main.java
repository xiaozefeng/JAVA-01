package io.github.mickey.week5.bytebuddy;

import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * @author mickey
 * @date 2/15/21 23:34
 */
public class Main {

    public static void main(String[] args) throws Exception {
        Service service = getBean(Service.class);
        service.bar(1000);
        service.foo(2000);
    }

    private static <T> T getBean(Class<?> clazz) throws Exception{
        if (clazz == null) return null;
        return (T) new ByteBuddy()
                .subclass(clazz)
                .method(ElementMatchers.any())
                .intercept(Advice.to(LogAdvisor.class))
                .make()
                .load(Thread.currentThread().getContextClassLoader())
                .getLoaded()
                .getDeclaredConstructor()
                .newInstance();
    }


}
