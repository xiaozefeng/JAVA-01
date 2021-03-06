package io.github.mickey.dynamic.datasource.v1.aop;

import io.github.mickey.dynamic.datasource.v1.annotation.DS;
import io.github.mickey.dynamic.datasource.v1.context.JdbcTemplateContext;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * @author mickey
 * @date 3/6/21 14:37
 */
@Aspect
@Component
public class DataSourceSwitchAspect {


    @Autowired
    private JdbcTemplate primaryJdbcTemplate;

    @Autowired
    @Qualifier("secondJdbcTemplate")
    private JdbcTemplate slaveJdbcTemplate;

    @Around("@annotation(ds)")
    public Object around(ProceedingJoinPoint pjp, DS ds) throws Throwable {
        if (ds != null && ds.value() == DS.Operation.MODIFY_DATA) {
            JdbcTemplateContext.set(primaryJdbcTemplate);
        } else {
            JdbcTemplateContext.set(slaveJdbcTemplate);
        }
        try {
            return pjp.proceed();
        }finally {
            JdbcTemplateContext.remove();
        }
    }
}
