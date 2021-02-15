package com.mickey.x3.starter.config;

import com.mickey.x3.starter.service.MessageService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author mickey
 * @date 2/15/21 17:35
 */
@Configuration
public class MickeyX3AutoConfiguration {

    @Bean
    public MessageService messageService() {
        return new MessageService();
    }
}
