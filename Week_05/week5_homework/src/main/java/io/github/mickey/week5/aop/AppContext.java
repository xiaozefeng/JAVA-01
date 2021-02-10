package io.github.mickey.week5.aop;

import io.github.mickey.week5.aop.handler.ProxyHandler;
import io.github.mickey.week5.aop.interceptor.Interceptor;

import java.lang.reflect.Proxy;
import java.util.ServiceLoader;

/**
 * @author mickey
 * @date 2/8/21 21:30
 */
public class AppContext {

    public AppContext() {
    }

    public <T> T getBean(Class<T> clazz) {
        Object target = getImpl(clazz);
        if (target == null) return null;
        Interceptor interceptor = getInterceptor();
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, new ProxyHandler(interceptor, target));
        return (T) o;
    }

    private Interceptor getInterceptor() {
        // just find one implement
        ServiceLoader<Interceptor> loader = ServiceLoader.load(Interceptor.class);
        return loader.findFirst().orElse(null);
    }

    private <T> Object getImpl(Class<T> clazz) {
        ServiceLoader<T> loader= ServiceLoader.load(clazz);
        return loader.findFirst().orElse(null);
    }

}
