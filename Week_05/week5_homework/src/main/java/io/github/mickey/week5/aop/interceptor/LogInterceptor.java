package io.github.mickey.week5.aop.interceptor;

/**
 * @author mickey
 * @date 2/8/21 21:56
 */
public class LogInterceptor implements Interceptor {
    @Override
    public boolean pre() {
        System.out.println("pre interceptor");
        return true;
    }

    @Override
    public void after() {
        System.out.println("after interceptor");
    }
}
