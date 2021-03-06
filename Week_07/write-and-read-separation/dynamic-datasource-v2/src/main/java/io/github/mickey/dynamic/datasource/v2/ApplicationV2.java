package io.github.mickey.dynamic.datasource.v2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author mickey
 * @date 3/6/21 14:28
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableAspectJAutoProxy
public class ApplicationV2 {

    public static void main(String[] args) {
        SpringApplication.run(ApplicationV2.class, args);
    }
}
