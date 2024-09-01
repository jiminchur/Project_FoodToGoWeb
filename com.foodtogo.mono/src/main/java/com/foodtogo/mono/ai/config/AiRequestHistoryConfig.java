package com.foodtogo.mono.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration // 이 클래스가 Spring의 설정 클래스임을 나타냄
public class AiRequestHistoryConfig {

    // RestTemplate 객체를 Spring의 컨테이너에 등록하는 메서드
    @Bean
    public RestTemplate restTemplate() {
        // RestTemplate은 HTTP 요청을 보내고 응답을 받기 위한 클래스
        return new RestTemplate(); // 새로운 RestTemplate 객체를 생성하여 반환
    }
}
