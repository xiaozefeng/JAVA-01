package io.github.mickey.datasource.horizontal.expand;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class DatasourceHorizontalExpandApplication {

    public static void main(String[] args) {
        SpringApplication.run(DatasourceHorizontalExpandApplication.class, args);
    }

}
