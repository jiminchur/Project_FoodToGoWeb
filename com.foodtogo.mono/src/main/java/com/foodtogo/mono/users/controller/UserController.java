package com.foodtogo.mono.users.controller;

import com.foodtogo.mono.users.dto.response.UserResponseDto;
import com.foodtogo.mono.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 정보 조회
    @GetMapping("/{user_id}")
    public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable("user_id") UUID findUserId) {

        UserResponseDto userInfo = userService.getUserInfo(findUserId);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }
}
