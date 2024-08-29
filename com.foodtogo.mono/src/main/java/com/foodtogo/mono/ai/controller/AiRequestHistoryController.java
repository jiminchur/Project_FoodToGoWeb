package com.foodtogo.mono.ai.controller;

import com.foodtogo.mono.ai.service.AiRequestHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
        return aiRequestHistoryService.generateContent(text,userId);
    }

}
