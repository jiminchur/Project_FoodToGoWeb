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

    @Value("${google.api.key}")
    private String apiKey;

    private final RestTemplate restTemplate;
    private final AiRequestHistoryRepository requestHistoryRepository;
    private final UserRepository userRepository;


    public String generateContent(
            String text,
            String userId
    ) {
        User user = findUser(userId);

        String url = "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash-latest:generateContent?key=" + apiKey;

        String requestBody = String.format("{\"contents\":[{\"parts\":[{\"text\":\"%s\"}]}]}", text + "한국어로 대답해주고 50자 이내로 이모지 추가해서 대답해줘");
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        HttpEntity<String> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.exchange(url, HttpMethod.POST, entity, String.class);

        AiRequestHistory aiRequestHistory = new AiRequestHistory(user,text,response.getBody(),userId);

        // 요청 기록 저장
        requestHistoryRepository.save(aiRequestHistory);

        return response.getBody();
    }

    // 전체조회
    public List<AiRequestHistory> getAllHistory(
    ) {
        return requestHistoryRepository.findAll();
    }

    // user_id로 찾기
    private User findUser(
            String userId
    ) {
        return userRepository.findById(UUID.fromString(userId))
                .orElseThrow(() -> new RuntimeException("사용자를 찾을 수 없습니다."));
    }
}


