package com.foodtogo.mono.user.service;

import com.foodtogo.mono.user.core.domain.User;
import com.foodtogo.mono.user.dto.request.UserUpdateRequestDto;
import com.foodtogo.mono.user.dto.response.UserResponseDto;
import com.foodtogo.mono.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
  
    // 회원 목록 조회 - MASTER
    @Transactional(readOnly = true)
    public Page<UserResponseDto> getUserList(int page, int size, String sortBy, boolean isAsc) {

        Pageable pageable = convertToPage(page, size, sortBy, isAsc);
        Page<User> userList = userRepository.findAll(pageable);

        return userList.map(UserResponseDto::new);
    }
  
    // 회원 검색 - MASTER
    @Transactional(readOnly = true)
    public Page<UserResponseDto> searchUserList(int page, int size, String sortBy, boolean isAsc, String keyword) {

        Pageable pageable = convertToPage(page, size, sortBy, isAsc);
        Page<User> userList = userRepository.findByUsernameContaining(keyword, pageable);
        
        return userList.map(UserResponseDto::new);
    }

    // 페이지 처리
    private Pageable convertToPage(int page, int size, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        return PageRequest.of(page, size, sort);
    }

    // 유저 찾는 공통 메소드
    public User findUserId(UUID userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }
}