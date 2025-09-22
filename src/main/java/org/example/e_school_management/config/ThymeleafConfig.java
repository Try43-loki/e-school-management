package org.example.e_school_management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestContextListener;

@Configuration
public class ThymeleafConfig {

    @Bean
    public RequestContextListener requestContextListener() {
        return new RequestContextListener();
    }
}
