package io.github.mickey.cache.aspect;

import com.google.common.cache.CacheBuilder;
import io.github.mickey.cache.annotation.Cache;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

/**
 * @author mickey
 * @date 2/18/21 00:23
 */
@Aspect
@Component
public class CacheAspect {

    @Pointcut("@annotation(io.github.mickey.cache.annotation.Cache)")
    public void pointcut() {
    }


    private final Map<String, com.google.common.cache.Cache<Object, Object>> m = new ConcurrentHashMap<>();


    @Around("pointcut()")
    public Object around(ProceedingJoinPoint pjp) {
        MethodSignature signature = (MethodSignature) pjp.getSignature();
        final Method method = signature.getMethod();
        final Cache cache = method.getAnnotation(Cache.class);
        final int timeout = cache.value();
        final String className = pjp.getTarget().getClass().getName();
        final String k = className + "." + method.getName();
        final com.google.common.cache.Cache<Object, Object> c = m.getOrDefault(k,
                CacheBuilder.newBuilder().expireAfterWrite(timeout, TimeUnit.SECONDS).build());
        final Object[] args = pjp.getArgs();
        try {
            StringBuilder key = new StringBuilder();
            for (Object arg : args) {
                key.append(arg.toString());
            }
            return c.get(key.toString(), () -> {
                try {
                    return pjp.proceed();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
                return null;
            });
        } catch (ExecutionException e) {
            e.printStackTrace();
        } finally {
            m.put(k, c);
        }
        return null;
    }
}
