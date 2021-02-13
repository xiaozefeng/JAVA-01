package io.github.mickey.spring.profile.example.config;

import io.github.mickey.spring.profile.example.DBConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

/**
 * @author mickey
 * @date 2/13/21 11:51
 */
@Configuration
public class EnvConfig {

    @Bean
    @Profile("dev")
    public DBConfig dev(){
        final DBConfig c = new DBConfig();
        c.setUrl("jdbc:mysql://localhost:3306/dev");
        c.setDriverClass("mysql");
        c.setUsername("root");
        c.setPassword("root");
        return c;
    }


    @Bean
    @Profile("prod")
    public DBConfig prod(){
        final DBConfig c = new DBConfig();
        c.setUrl("jdbc:mysql://localhost:3306/prod");
        c.setDriverClass("mysql");
        c.setUsername("root");
        c.setPassword("123456789");
        return c;
    }
}
