package com.foodtogo.mono.user.controller;

import com.foodtogo.mono.user.dto.request.SignupRequestDto;
import com.foodtogo.mono.user.dto.request.UserUpdateRequestDto;
import com.foodtogo.mono.user.dto.response.UserResponseDto;
import com.foodtogo.mono.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<String> signupUser(@Valid SignupRequestDto signupRequestDto) {

        String message = userService.signupUser(signupRequestDto);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // 회원 정보 조회
    @GetMapping("/{user_id}")
    public ResponseEntity<UserResponseDto> getUserInfo(@RequestHeader("X-User-Id") UUID userId,
                                                       @PathVariable("user_id") UUID findUserId) {

        UserResponseDto userInfo = userService.getUserInfo(userId, findUserId);
        return new ResponseEntity<>(userInfo, HttpStatus.OK);
    }

    // 회원 정보 수정
    @PutMapping("/{user_id}")
    public ResponseEntity<UserResponseDto> updateUserInfo(@RequestHeader("X-User-Id") UUID userId,
                                                          @PathVariable("user_id") UUID updateUserId,
                                                          UserUpdateRequestDto userUpdateRequestDto) {

        UserResponseDto updateUserInfo = userService.updateUserInfo(userUpdateRequestDto, userId, updateUserId);
        return new ResponseEntity<>(updateUserInfo, HttpStatus.OK);
    }

    // 회원 삭제
    @DeleteMapping("/{user_id}")
    public ResponseEntity<String> deleteUser(@RequestHeader("X-User-Id") UUID userId,
                                             @PathVariable("user_id") UUID deleteUserId) {

        String message = userService.deleteUser(userId, deleteUserId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }

    // 회원 목록 조회 - MASTER
    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getUserList(@RequestHeader("X-Role") String role,
                                                             @RequestParam("page") int page,
                                                             @RequestParam("size") int size,
                                                             @RequestParam("sortBy") String sortBy,
                                                             @RequestParam("isAsc") boolean isAsc) {
        validateRole(role);
        Page<UserResponseDto> userList = userService.getUserList(page, size, sortBy, isAsc);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    // 회원 검색 - MASTER
    @GetMapping("/search")
    public ResponseEntity<Page<UserResponseDto>> searchUser(@RequestHeader("X-Role") String role,
                                                            @RequestParam("page") int page,
                                                            @RequestParam("size") int size,
                                                            @RequestParam("sortBy") String sortBy,
                                                            @RequestParam("isAsc") boolean isAsc,
                                                            @RequestParam("keyword") String keyword) {
        validateRole(role);
        Page<UserResponseDto> searchedUser = userService.searchUserList(page, size, sortBy, isAsc, keyword);
        return new ResponseEntity<>(searchedUser, HttpStatus.OK);
    }

    // 권한 체크
    private void validateRole(String role) {
        if (!"MASTER".equals(role) && !"MANAGER".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. User role is not MANAGER or MANAGER.");
        }
    }
}
