package io.github.mickey.week5.aop;

import io.github.mickey.week5.aop.annotation.Service;
import org.reflections.Reflections;

import java.util.Set;

/**
 * @author mickey
 * @date 2/10/21 23:54
 */
public class Test {
    public static void main(String[] args) {
        //List<String> classNames = ClassUtil.getClassNames("io.github.mickey.week5.aop");
        //classNames.forEach(System.out::println);


        Reflections reflections = new Reflections("io.github.mickey.week5.aop");
        Set<Class<?>> typesAnnotatedWith = reflections.getTypesAnnotatedWith(Service.class);
        typesAnnotatedWith.forEach(System.out::println);

        //Set<Class<? extends LogInterceptor>> subTypesOf = reflections.getSubTypesOf(LogInterceptor.class);
        //subTypesOf.forEach(System.out::println);
        //reflections.get
    }
}
