package com.foodtogo.mono.users.controller;

import com.foodtogo.mono.users.dto.response.UserResponseDto;
import com.foodtogo.mono.users.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;


    // 회원 목록 조회 - MASTER
    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getUserList(@RequestParam("page") int page,
                                                             @RequestParam("size") int size,
                                                             @RequestParam("sortBy") String sortBy,
                                                             @RequestParam("isAsc") boolean isAsc) {
        Page<UserResponseDto> userList = userService.getUserList(page, size, sortBy, isAsc);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
}