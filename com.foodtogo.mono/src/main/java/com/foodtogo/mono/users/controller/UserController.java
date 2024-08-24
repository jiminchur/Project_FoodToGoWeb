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


    // 회원 검색 - MASTER
    @GetMapping("/search")
    public ResponseEntity<Page<UserResponseDto>> searchUser(@RequestParam("page") int page,
                                                            @RequestParam("size") int size,
                                                            @RequestParam("sortBy") String sortBy,
                                                            @RequestParam("isAsc") boolean isAsc,
                                                            @RequestParam("keyword") String keyword) {
        Page<UserResponseDto> searchedUser = userService.searchUserList(page, size, sortBy, isAsc, keyword);
        return new ResponseEntity<>(searchedUser, HttpStatus.OK);
    }
}