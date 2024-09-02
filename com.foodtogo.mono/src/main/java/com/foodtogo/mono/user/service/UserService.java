package com.foodtogo.mono.user.service;

import com.foodtogo.mono.user.core.domain.User;
import com.foodtogo.mono.user.core.enums.UserRoleEnum;
import com.foodtogo.mono.user.dto.request.ChangePasswordRequestDto;
import com.foodtogo.mono.user.dto.request.SignupRequestDto;
import com.foodtogo.mono.user.dto.request.UpdateRequestDto;
import com.foodtogo.mono.user.dto.response.UserResponseDto;
import com.foodtogo.mono.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    @Value("${role.manager}")
    private String managerToken;

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원 가입
    @Transactional
    public String signup(SignupRequestDto requestDto) {
        // 이메일 중복 확인
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }

        // 사용자 ROLE 확인
        final UserRoleEnum role = UserRoleEnum.getRoleEnum(requestDto.getRole());

        // Manager Token validation
        if (role.equals(UserRoleEnum.MANAGER) && !managerToken.equals(requestDto.getAdminToken())) {
            throw new IllegalArgumentException("매니저 암호가 틀려 등록이 불가능합니다.");
        }

        // 비밀번호 암호화
        final String passwordEncode = passwordEncoder.encode(requestDto.getPassword());

        // 회원 등록
        userRepository.save(User.create(requestDto, passwordEncode, role));
        return requestDto.getEmail();
    }

    // 회원 한명 정보 조회
    @Transactional(readOnly = true)
    public UserResponseDto getUserInfo(UUID userId) {
        User user = userRepository.findUserId(userId);
        return UserResponseDto.fromUser(user);
    }

    // 회원 정보 수정
    @Transactional
    public void updateUserInfo(UpdateRequestDto requestDto, UUID userId) {
        User user = userRepository.findUserId(userId);

        // 비밀번호 암호화
        final String passwordEncode = passwordEncoder.encode(requestDto.getPassword());

        user.updateUserInfo(requestDto, passwordEncode);
    }

    // 회원 삭제
    @Transactional
    public void deleteUser(UUID deleteUserId, UUID loginUserId) {
        // 타겟 유저 정보
        User user = userRepository.findUserByIdOrThrowException(deleteUserId);
        user.delete(loginUserId.toString());
    }


    // 회원 목록 조회 (페이지, 정렬 옵션 포함)
    // 정렬기능은 기본적으로 생성일순, 수정일순을 기준
    @Transactional(readOnly = true)
    public Page<UserResponseDto> getAllUsers(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        Page<User> userPage = userRepository.findAll(pageable);

        // User 엔티티를 UserResponseDto로 변환
        return userPage.map(UserResponseDto::fromUser);
    }

    // 회원 검색 (페이지, 정렬 옵션 포함)
    // 정렬기능은 기본적으로 생성일순, 수정일순을 기준
    // 10건, 30건, 50건 기준으로 페이지에 노출 될수 있습니다, 기본 10건씩으로 고정
    @Transactional(readOnly = true)
    public Page<UserResponseDto> searchUsers(String keyword, int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy).descending());

        // 특정 키워드가 포함된 사용자 검색
        Page<User> userPage = userRepository.findByUsernameContaining(keyword, pageable);

        // User 엔티티를 UserResponseDto로 변환
        return userPage.map(UserResponseDto::fromUser);
    }

    @Transactional
    public void changePassword(ChangePasswordRequestDto requestDto, UUID loginUserId) {
        User user = userRepository.findUserId(requestDto.getUserId());

        // 현재 비밀번호 확인
        if (!passwordEncoder.matches(requestDto.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("현재 비밀번호가 틀렸습니다.");
        }

        // 새 비밀번호 암호화
        final String newPasswordEncoded = passwordEncoder.encode(requestDto.getNewPassword());

        // 비밀번호 변경
        user.changePassword(newPasswordEncoded);
        user.setUpdatedBy(loginUserId.toString());
    }

    public User getUserByUserId(UUID userId) {
        return userRepository.findUserId(userId);
    }
    
    public User getUserByEmail(String email) {
        return userRepository.findEmail(email);
    }
}