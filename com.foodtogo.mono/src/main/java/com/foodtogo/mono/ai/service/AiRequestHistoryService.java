package com.foodtogo.mono.ai.service;

import com.foodtogo.mono.ai.core.AiRequestHistory;
import com.foodtogo.mono.ai.repository.AiRequestHistoryRepository;
import com.foodtogo.mono.user.core.domain.User;
import com.foodtogo.mono.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AiRequestHistoryService {

    @Value("${api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final AiRequestHistoryRepository requestHistoryRepository;
    private final UserRepository userRepository;


    // 콘텐츠 생성 메서드
    public String generateContent(String text, String userId) {
        User user = findUser(userId); // 사용자 찾기

        // API 호출 URL
        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=" + apiKey;

        // 요청 본문 설정
        String requestBody = String.format("{\"contents\":[{\"parts\":[{\"text\":\"%s\"}]}]}", text + " 한국어로 대답해주고 50자 이내로 이모지 추가해서 대답해줘");

        // HTTP 헤더 설정
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // HTTP 요청 엔티티 생성
        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);

        // REST API 호출
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        // 요청 기록 객체 생성
        AiRequestHistory aiRequestHistory = new AiRequestHistory(user, text, response.getBody(), userId);

        // 요청 기록 저장
        requestHistoryRepository.save(aiRequestHistory);

        return response.getBody(); // 응답 본문 반환
    }

    // 전체 요청 히스토리 조회 메서드
    public List<AiRequestHistory> getAllHistory() {
        return requestHistoryRepository.findAll(); // 모든 요청 히스토리 반환
    }

    // 사용자 ID로 사용자 찾기
    private User findUser(String userId) {
        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다.")); // 사용자 없을 경우 예외 발생
    }
}


