package io.github.mickey.util;

import io.github.mickey.config.SpringConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author mickey
 * @date 2/11/21 15:20
 */
public final class ApplicationContextUtil {

    private static final ApplicationContext ac;

    public static ApplicationContext getApplicationContext() {
        return ac;
    }

    static{
        ac = new AnnotationConfigApplicationContext(SpringConfig.class);
    }
}
