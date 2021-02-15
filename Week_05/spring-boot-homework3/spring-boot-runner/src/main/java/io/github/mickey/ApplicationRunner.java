package io.github.mickey;

import com.mickey.x3.starter.annotation.EnableMickeyMessageX1;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author mickey
 * @date 2/15/21 16:42
 */
@SpringBootApplication
@EnableMickeyMessageX1
//@EnableMickeyMessageX2
public class ApplicationRunner {
    public static void main(String[] args) {
        SpringApplication.run(ApplicationRunner.class, args);
    }
}
