package io.github.mickey.spring.profile.example;

import io.github.mickey.spring.profile.example.config.AppConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author mickey
 * @date 2/13/21 11:14
 */
public class App {
    public static void main(String[] args) {
        //System.setProperty(AbstractEnvironment.ACTIVE_PROFILES_PROPERTY_NAME, "dev");
        AnnotationConfigApplicationContext ac = new AnnotationConfigApplicationContext(AppConfig.class);
        final DBConfig c = ac.getBean(DBConfig.class);
        System.out.println(c);
        ac.close();
    }
}
