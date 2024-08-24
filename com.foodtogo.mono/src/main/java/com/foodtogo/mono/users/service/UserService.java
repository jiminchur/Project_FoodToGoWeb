package com.foodtogo.mono.users.service;

import com.foodtogo.mono.users.dto.request.UserUpdateRequestDto;
import com.foodtogo.mono.users.dto.response.UserResponseDto;
import com.foodtogo.mono.users.entity.User;
import com.foodtogo.mono.users.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

  
    // 회원 정보 조회
    @Transactional(readOnly = true)
    public UserResponseDto getUserInfo(UUID userId) {

        User user = findUserId(userId);

        return new UserResponseDto(user);
    }
    
    // 회원 정보 수정
    @Transactional
    public UserResponseDto updateUserInfo(UserUpdateRequestDto requestDto, UUID userId) {

        User user = findUserId(userId);
        user.updateUserInfo(requestDto, user.getUsername());

        return new UserResponseDto(user);
    }
  
    // 회원 삭제
    @Transactional
    public String deleteUser(UUID userId) {

        User user = findUserId(userId);
        user.deleteUser(user.getUsername());

        return "[" + user.getUsername() + "]님 회원 정보 삭제 완료";
    }

    // 유저 찾는 공통 메소드
    public User findUserId(UUID userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }
}