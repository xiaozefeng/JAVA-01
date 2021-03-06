package io.github.mickey.dynamic.datasource.v1.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author mickey
 * @date 3/6/21 14:41
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface DS {

    Operation value() default Operation.READ_ONLY;

    enum Operation {
        READ_ONLY,
        MODIFY_DATA,
    }
}
