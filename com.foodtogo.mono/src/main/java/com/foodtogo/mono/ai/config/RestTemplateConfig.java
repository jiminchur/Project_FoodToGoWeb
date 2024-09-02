package com.foodtogo.mono.ai.config;

import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;

@Configuration // 이 클래스가 Spring의 설정 클래스임을 나타냄
public class RestTemplateConfig {

    // RestTemplate 객체를 Spring의 컨테이너에 등록하는 메서드
    @Bean
    public RestTemplate restTemplate(RestTemplateBuilder builder) {
        return builder
                .setConnectTimeout(Duration.ofSeconds(60))  // 연결 타임아웃 설정
                .setReadTimeout(Duration.ofSeconds(60))     // 읽기 타임아웃 설정
                .build();
    }
}