package com.mickey.x2.starter.config;

import com.mickey.x2.starter.service.MickeyService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mickey
 * @date 2/15/21 16:34
 */
@Configuration
public class MickeyConfig {

    @Bean
    public MickeyService mickeyService() {
        return new MickeyService();
    }
}
