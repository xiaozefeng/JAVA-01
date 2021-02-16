package io.github.mickey.week5.bytebuddy;


import net.bytebuddy.ByteBuddy;
import net.bytebuddy.asm.Advice;
import net.bytebuddy.matcher.ElementMatchers;

/**
 * @author mickey
 * @date 2/16/21 22:06
 */
public class BeanFactory {

    private BeanFactory() {

    }

    public  static  <T> T  getBean(Class<T> clazz) {
        if (clazz == null) return null;
        try {
            return (T) new ByteBuddy()
                    .subclass(clazz)
                    .method(ElementMatchers.any())
                    .intercept(Advice.to(LogAdvisor.class))
                    .make()
                    .load(Thread.currentThread().getContextClassLoader())
                    .getLoaded()
                    .getDeclaredConstructor()
                    .newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
