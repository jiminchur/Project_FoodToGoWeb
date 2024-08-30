package com.foodtogo.mono.ai.controller;

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

    @PostMapping("/generate-content")
    public String generateContent(
            @RequestBody String text,
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestHeader(value = "X-Role") String role
    ) {
        if (!"MASTER".equals(role) && !"OWNER".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. User role is not MANAGER or OWNER.");
        }
        return aiRequestHistoryService.generateContent(text,userId);
    }

    @GetMapping("/history")
    public List<AiRequestHistory> getHistory(
        @RequestHeader(value = "X-User-Id") String userId,
        @RequestHeader(value = "X-Role") String role
    ){
        if (!"MASTER".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. User role is not MANAGER or OWNER.");
        }

        return aiRequestHistoryService.getAllHistory();
    }
}
