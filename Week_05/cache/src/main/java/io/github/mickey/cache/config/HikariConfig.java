package io.github.mickey.cache.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author mickey
 * @date 2/17/21 23:23
 */
@Configuration
public class HikariConfig {


    @Bean("dbConfig")
    public Properties dbConfig() {
        final InputStream is = HikariConfig.class.getClassLoader().getResourceAsStream("db.properties");
        final Properties p = new Properties();
        try {
            p.load(is);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return p;
    }

    @Bean
    public HikariDataSource dataSource(@Autowired @Qualifier("dbConfig") Properties p){
        com.zaxxer.hikari.HikariConfig config = new com.zaxxer.hikari.HikariConfig();
        config.setJdbcUrl(p.getProperty("db.url"));
        config.setUsername(p.getProperty("db.username"));
        config.setPassword(p.getProperty("db.password"));
        return new HikariDataSource(config);
    }

}
