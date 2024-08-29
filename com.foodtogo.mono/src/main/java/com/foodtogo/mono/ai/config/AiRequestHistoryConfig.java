package com.foodtogo.mono.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class AiRequestHistoryConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
