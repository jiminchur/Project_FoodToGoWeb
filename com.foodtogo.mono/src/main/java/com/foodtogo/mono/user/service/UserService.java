package com.foodtogo.mono.user.service;

import com.foodtogo.mono.user.core.domain.User;
import com.foodtogo.mono.user.dto.request.SignupRequestDto;
import com.foodtogo.mono.user.dto.request.UserUpdateRequestDto;
import com.foodtogo.mono.user.dto.response.UserResponseDto;
import com.foodtogo.mono.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    // 회원 가입
    @Transactional
    public String signupUser(SignupRequestDto signupRequestDto) {
        // 닉네임 중복 확인
        if (userRepository.existsByNickname(signupRequestDto.getNickname())) {
            throw new IllegalArgumentException("사용 중인 유저명 입니다.");
        }
        // 이메일 중복 확인
        if (userRepository.existsByEmail(signupRequestDto.getEmail())) {
            throw new IllegalArgumentException("사용 중인 이메일 입니다.");
        }
        // 전화번호 중복 확인
        if (userRepository.existsByPhoneNumber(signupRequestDto.getPhoneNumber())) {
            throw new IllegalArgumentException("사용 중인 전화번호 입니다.");
        }
        // 관리자 체크 한번 해야됨.

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(signupRequestDto.getPassword());
        // 회원 등록
        User user = new User(signupRequestDto, encodedPassword);
        userRepository.save(user);

        return "[" + user.getUsername() + "]회원님 가입을 환영합니다.";
    }

    // 회원 정보 조회
    @Transactional(readOnly = true)
    public UserResponseDto getUserInfo(UUID userId, UUID findUserId) {
        validateUserAccess(userId, findUserId);
        // 타겟 유저 정보
        User user = findUserId(findUserId);

        return new UserResponseDto(user);
    }

    // 회원 정보 수정
    @Transactional
    public UserResponseDto updateUserInfo(UserUpdateRequestDto requestDto, UUID userId, UUID updateUserId) {
        validateUserAccess(userId, updateUserId);
        // 타겟 유저 정보
        User user = findUserId(updateUserId);
        user.updateUserInfo(requestDto, user.getUsername());

        return new UserResponseDto(user);
    }

    // 회원 삭제
    @Transactional
    public String deleteUser(UUID userId, UUID deleteUserId) {
        validateUserAccess(userId, deleteUserId);
        // 타겟 유저 정보
        User user = findUserId(deleteUserId);
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
    private User findUserId(UUID userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 유저입니다."));
    }

    // 접근하려는 유저와 현재 로그인한 유저가 동일인인지 확인하는 공통 메소드
    private void validateUserAccess(UUID loggedInUser, UUID targetUserId) {
        if (!loggedInUser.equals(targetUserId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "회원님의 정보가 아닙니다.");
        }
    }
}