package io.github.mickey.week5.aop.handler;

import io.github.mickey.week5.aop.Interceptor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author mickey
 * @date 2/8/21 21:48
 */
public class ProxyHandler implements InvocationHandler {

    private final Interceptor interceptor;

    private final Object target;

    public ProxyHandler(Interceptor interceptor, Object target) {
        this.interceptor = interceptor;
        this.target = target;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            boolean pre = interceptor.pre();
            if (!pre) return null;
            return method.invoke(target, args);
        } finally {
            interceptor.after();
        }
    }
}
