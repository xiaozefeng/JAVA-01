package io.github.mickey.shardingsphere.v1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.transaction.jta.JtaAutoConfiguration;

/**
 * @author mickey
 * @date 3/7/21 00:22
 */
@SpringBootApplication(exclude = JtaAutoConfiguration.class)
public class ShardingSpehereApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShardingSpehereApplication.class, args);
    }
}
