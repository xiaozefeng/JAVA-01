package io.github.mickey.week5.bytebuddy;

import io.github.mickey.week5.bytebuddy.annotaion.Log;
import net.bytebuddy.asm.Advice;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author mickey
 * @date 2/15/21 23:35
 */
public class LogAdvisor {

    @Advice.OnMethodEnter
    public static void onMethodEnter(@Advice.Origin Method method,
                                     @Advice.AllArguments Object[] args) {
        if (method.getAnnotation(Log.class) != null) {
            System.out.println("pre logic...., args:" + Arrays.toString(args));
        }
    }

    @Advice.OnMethodExit
    public static void onMethodExit(@Advice.Origin Method method,
                                    @Advice.AllArguments Object[] args,
                                    @Advice.Return Object ret) {
        if (method.getAnnotation(Log.class) != null) {
            System.out.println("after logic.. args: " + Arrays.toString(args) + " return val:" + ret);
        }
    }

}
