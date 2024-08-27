package com.foodtogo.mono.user.controller;

import com.foodtogo.mono.user.dto.request.UserUpdateRequestDto;
import com.foodtogo.mono.user.dto.response.UserResponseDto;
import com.foodtogo.mono.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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
  
    // 회원 정보 수정
    @PutMapping("/{user_id}")
    public ResponseEntity<UserResponseDto> updateUserInfo(@PathVariable("user_id") UUID updateUserId,
                                                          UserUpdateRequestDto userUpdateRequestDto) {

        UserResponseDto updateUserInfo = userService.updateUserInfo(userUpdateRequestDto, updateUserId);
        return new ResponseEntity<>(updateUserInfo, HttpStatus.OK);

    }
  
    // 회원 삭제
    @DeleteMapping("/{user_id}")
    public ResponseEntity<String> deleteUser(@PathVariable("user_id") UUID deleteUserId) {

        String message = userService.deleteUser(deleteUserId);
        return new ResponseEntity<>(message, HttpStatus.OK);
    }
  
    // 회원 목록 조회 - MASTER
    @GetMapping
    public ResponseEntity<Page<UserResponseDto>> getUserList(@RequestParam("page") int page,
                                                             @RequestParam("size") int size,
                                                             @RequestParam("sortBy") String sortBy,
                                                             @RequestParam("isAsc") boolean isAsc) {
        Page<UserResponseDto> userList = userService.getUserList(page, size, sortBy, isAsc);
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }
  
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
