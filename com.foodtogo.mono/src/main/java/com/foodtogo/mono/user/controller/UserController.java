package com.foodtogo.mono.user.controller;

import static com.foodtogo.mono.user.core.enums.UserRoleEnum.validateManagerOrMaster;

import com.foodtogo.mono.Result;
import com.foodtogo.mono.user.dto.request.ChangePasswordRequestDto;
import com.foodtogo.mono.user.dto.request.SignupRequestDto;
import com.foodtogo.mono.user.dto.request.UpdateRequestDto;
import com.foodtogo.mono.user.dto.request.UserSearchDto;
import com.foodtogo.mono.user.dto.response.UserResponseDto;
import com.foodtogo.mono.user.service.AuthService;
import com.foodtogo.mono.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j(topic = "UserController")
@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final AuthService authService;

    // 회원 가입
    @PostMapping("/signup")
    public ResponseEntity<Result<String>> signupUser(@Valid @RequestBody SignupRequestDto signupRequestDto) {

        final String email = userService.signup(signupRequestDto);

        return new ResponseEntity<>(Result.of(email), HttpStatus.OK);
    }

    // 회원 정보 조회
    @GetMapping("/{userId}")
    public ResponseEntity<Result<UserResponseDto>> getUserInfo(@PathVariable("userId") UUID userId) {

        UserResponseDto userInfo = userService.getUserInfo(userId);
        return new ResponseEntity<>(Result.of(userInfo), HttpStatus.OK);
    }

    // 회원 정보 수정
    @PutMapping("/{userId}")
    public ResponseEntity<Result<UserResponseDto>> updateUserInfo(@PathVariable("userId") UUID userId, @RequestBody UpdateRequestDto updateRequestDto) {
        userService.updateUserInfo(updateRequestDto, userId);
        UserResponseDto userInfo = userService.getUserInfo(userId);

        try {
            authService.updateRedisUserRole(userId, userInfo.getRole().toString());
        } catch (Exception e) {
            log.warn("Failed to update Redis cache for user role: {}", e.getMessage());
        }
        
        return new ResponseEntity<>(Result.of(userInfo), HttpStatus.OK);
    }

    // 회원 삭제
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") UUID deleteUserId, @RequestHeader("X-User-Id") UUID loginUserId) {

        userService.deleteUser(deleteUserId, loginUserId);
        try {
            authService.deleteRedisUserRole(deleteUserId);
            // 해당 유저의 토큰을 가져와서 블랙리스트 추가

        } catch (Exception e) {
            log.warn("Failed to delete user role from Redis cache: {}", e.getMessage());
        }

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    // 회원 목록 조회 엔드포인트
    @GetMapping
    public ResponseEntity<Result<Page<UserResponseDto>>> getAllUsers(
            @RequestHeader("X-Role") String role,
            UserSearchDto searchDto) {

        validateManagerOrMaster(role);

        // 페이지 크기를 10, 30, 50으로 제한
        int size = searchDto.getValidatedSize();
        Page<UserResponseDto> users = userService.getAllUsers(searchDto.getPage(), size, searchDto.getSortBy());
        return new ResponseEntity<>(Result.of(users), HttpStatus.OK);
    }


    // 회원 검색 엔드포인트
    @GetMapping("/search")
    public ResponseEntity<Result<Page<UserResponseDto>>> searchUsers(
            @RequestHeader("X-Role") String role,
            UserSearchDto searchDto) {

        validateManagerOrMaster(role);

        // 페이지 크기를 10, 30, 50으로 제한
        int size = searchDto.getValidatedSize();
        Page<UserResponseDto> users = userService.searchUsers(searchDto.getKeyword(), searchDto.getPage(), size, searchDto.getSortBy());
        return new ResponseEntity<>(Result.of(users), HttpStatus.OK);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<Result<String>> changePassword(
            @RequestBody @Valid ChangePasswordRequestDto changePasswordRequestDto,
            @RequestHeader("X-User-Id") UUID loginUserId) {

        userService.changePassword(changePasswordRequestDto, loginUserId);
        return new ResponseEntity<>(Result.of("비밀번호가 성공적으로 변경되었습니다."), HttpStatus.OK);
    }
}
