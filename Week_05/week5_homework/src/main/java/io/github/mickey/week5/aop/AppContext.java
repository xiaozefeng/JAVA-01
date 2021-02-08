package io.github.mickey.week5.aop;

import io.github.mickey.week5.aop.handler.ProxyHandler;

import java.lang.reflect.Proxy;

/**
 * @author mickey
 * @date 2/8/21 21:30
 */
public class AppContext {

    public AppContext() {
    }

    public <T> T getBean(Class<T> clazz) {
        Object target = getImpl(clazz);
        Interceptor interceptor = getInterceptor();
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new ProxyHandler(interceptor, target));
        return (T) o;
    }

    private Interceptor getInterceptor() {
        return new CustomInterceptor();
    }

    private <T> Object getImpl(Class<T> clazz) {
        return new FooImpl();
    }

}
