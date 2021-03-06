package io.github.mickey.dynamic.datasource.v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author mickey
 * @date 3/6/21 14:28
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class ApplicationV1 {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationV1.class, args);
    }
}
