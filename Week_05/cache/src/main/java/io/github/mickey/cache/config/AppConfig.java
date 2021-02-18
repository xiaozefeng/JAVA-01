package io.github.mickey.cache.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author mickey
 * @date 2/18/21 00:24
 */
@Configuration
@ComponentScan("io.github.mickey.cache")
@EnableAspectJAutoProxy
public class AppConfig {

}
