package com.foodtogo.mono.ai.controller;

import com.foodtogo.mono.Result;
import com.foodtogo.mono.ai.core.AiRequestHistory;
import com.foodtogo.mono.ai.service.AiRequestHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ai")
@RequiredArgsConstructor
public class AiRequestHistoryController {

    private final AiRequestHistoryService aiRequestHistoryService;

    // 콘텐츠 생성을 위한 POST 요청 처리
    @PostMapping("/generate-content")
    public Result<String> generateContent(
            @RequestBody String text, // 요청 본문에서 받은 텍스트
            @RequestHeader(value = "X-User-Id") String userId, // 요청 헤더에서 사용자 ID
            @RequestHeader(value = "X-Role") String role // 요청 헤더에서 사용자 역할
    ) {
        // 사용자 역할이 MASTER 또는 OWNER가 아닐 경우 접근 거부
        if (!"MASTER".equals(role) && !"OWNER".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. User role is not MANAGER or OWNER.");
        }
        // 콘텐츠 생성 서비스 호출
        return Result.of(aiRequestHistoryService.generateContent(text, userId));
    }

    // 요청 히스토리를 가져오기 위한 GET 요청 처리
    @GetMapping("/history")
    public Result<List<AiRequestHistory>> getHistory(
            @RequestHeader(value = "X-User-Id") String userId, // 요청 헤더에서 사용자 ID
            @RequestHeader(value = "X-Role") String role // 요청 헤더에서 사용자 역할
    ) {
        // 사용자 역할이 MASTER가 아닐 경우 접근 거부
        if (!"MASTER".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. User role is not MANAGER or OWNER.");
        }

        // 히스토리 서비스 호출
        return Result.of(aiRequestHistoryService.getAllHistory());
    }
}
