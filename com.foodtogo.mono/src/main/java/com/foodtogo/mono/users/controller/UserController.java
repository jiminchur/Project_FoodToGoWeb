package com.foodtogo.mono.users.controller;

import com.foodtogo.mono.users.dto.request.UserUpdateRequestDto;
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

    // 회원 정보 수정
    @PutMapping("/{user_id}")
    public ResponseEntity<UserResponseDto> updateUserInfo(@PathVariable("user_id") UUID updateUserId,
                                                          UserUpdateRequestDto userUpdateRequestDto) {

        UserResponseDto updateUserInfo = userService.updateUserInfo(userUpdateRequestDto, updateUserId);
        return new ResponseEntity<>(updateUserInfo, HttpStatus.OK);
    }
}