package io.github.mickey.agent.x3;

import net.bytebuddy.implementation.bind.annotation.Origin;
import net.bytebuddy.implementation.bind.annotation.RuntimeType;
import net.bytebuddy.implementation.bind.annotation.SuperCall;

import java.lang.reflect.Method;
import java.util.concurrent.Callable;

/**
 * @author mickey
 * @date 2/16/21 23:14
 */
public class TimeInterceptor {
    @RuntimeType
        public static Object intercept(@Origin Method method,
                                       @SuperCall Callable<?> call) throws Exception{
        final long start = System.currentTimeMillis();
        try {
            return call.call();
        }finally {
            final long end = System.currentTimeMillis();
            System.out.println(method.getName() + ": took " + (end-start) + " ms");
        }
    }
}
