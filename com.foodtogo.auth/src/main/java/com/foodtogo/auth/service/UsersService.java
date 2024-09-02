package com.foodtogo.auth.service;

import com.foodtogo.auth.dto.request.LoginRequestDto;
import com.foodtogo.auth.dto.request.SignupRequestDto;
import com.foodtogo.auth.dto.response.LoginResponseDto;
import com.foodtogo.auth.jwt.JwtUtil;
import com.foodtogo.auth.repository.UserRepository;
import com.foodtogo.auth.user.core.domain.User;
import com.foodtogo.auth.user.core.enums.UserRoleEnum;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@Slf4j(topic = "UsersService")
@Service
public class UsersService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public UsersService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }


    @Value("${spring.role.manager}")
    private String managerToken;


    public LoginResponseDto login(LoginRequestDto requestDto, HttpServletResponse res) {
        final String email = requestDto.getEmail();
        final String password = requestDto.getPassword();

        // 사용자 확인
        final User user = userRepository.findByEmail(email).orElseThrow(
                () -> new IllegalArgumentException("존재하지 않는 회원입니다.")
        );

        // 비밀번호 확인
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 생성 및 쿠키에 저장 후 Response 객체에 추가
        final String token = jwtUtil.createToken(user.getUserId().toString(), user.getRole());

        //쿠키 저장
        jwtUtil.addJwtToCookie(token, res);
        return LoginResponseDto.builder()
                .token(token)
                .userId(user.getUserId())
                .role(user.getRole())
                .build();
    }

    @Transactional
    public void signup(SignupRequestDto requestDto) {

        // email 중복확인
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
        userRepository.save(User.createUser(requestDto, passwordEncode, role));
    }

    @Transactional(readOnly = true)
    public String findRoleByUserId(String userId) {
        return userRepository.findRoleByUserId(UUID.fromString(userId)).orElseThrow(() -> new NoSuchElementException("User not found"));
    }
}
