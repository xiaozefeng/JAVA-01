package io.github.mickey.week5.aop;

import io.github.mickey.week5.aop.handler.ProxyHandler;
import io.github.mickey.week5.aop.interceptor.Interceptor;

import java.lang.reflect.Proxy;
import java.util.Map;
import java.util.ServiceLoader;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author mickey
 * @date 2/8/21 21:30
 */
public class AppContext {

    private final Map<String, Object> CACHE = new ConcurrentHashMap<>();

    public AppContext() {
    }

    @SuppressWarnings("ALL")
    public <T> T getBean(Class<T> clazz) {
        Object cacheObj = CACHE.get(clazz.getName());
        if (cacheObj != null)
            return (T) cacheObj;

        Object target = getImpl(clazz);
        if (target == null) return null;
        Interceptor interceptor = getInterceptor();

        Object result = getProxy(clazz, target, interceptor);

        CACHE.put(clazz.getName(), result);
        return (T) result;
    }

    private <T> Object getProxy(Class<T> clazz, Object target, Interceptor interceptor) {
        return Proxy.newProxyInstance(clazz.getClassLoader(),
                new Class[]{clazz},
                new ProxyHandler(interceptor, target));
    }

    private Interceptor getInterceptor() {
        // just find one implement
        ServiceLoader<Interceptor> loader = ServiceLoader.load(Interceptor.class);
        return loader.findFirst().orElse(null);
    }

    private <T> Object getImpl(Class<T> clazz) {
        // just find one implement
        ServiceLoader<T> loader= ServiceLoader.load(clazz);
        return loader.findFirst().orElse(null);
    }

}
