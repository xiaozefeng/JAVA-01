package io.github.mickey.week5.assemble;

import io.github.mickey.week5.assemble.auto.AService;
import io.github.mickey.week5.assemble.auto.DIConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author mickey
 * @date 2/11/21 01:26
 */
public class AutoAssemble {
    public static void main(String[] args) {
        ApplicationContext ac = new AnnotationConfigApplicationContext(DIConfig.class);
        AService a = ac.getBean(AService.class);
        a.hi();
    }
}
