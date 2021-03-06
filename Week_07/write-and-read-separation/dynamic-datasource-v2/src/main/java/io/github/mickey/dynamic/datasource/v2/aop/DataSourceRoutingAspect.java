package io.github.mickey.dynamic.datasource.v2.aop;

import io.github.mickey.dynamic.datasource.v2.annotation.DS;
import io.github.mickey.dynamic.datasource.v2.context.DataSourceRoutingContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @author mickey
 * @date 3/6/21 14:37
 */
@Aspect
@Component
public class DataSourceRoutingAspect {

    @Around("@annotation(ds)")
    public Object around(ProceedingJoinPoint pjp, DS ds) throws Throwable {
        try (DataSourceRoutingContext context = new DataSourceRoutingContext(ds.value())) {
            return pjp.proceed();
        }
    }
}
