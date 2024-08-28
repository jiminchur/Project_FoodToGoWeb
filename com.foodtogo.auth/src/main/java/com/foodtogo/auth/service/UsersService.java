package com.foodtogo.auth.service;

import com.foodtogo.auth.dto.LoginRequestDto;
import com.foodtogo.auth.dto.LoginResponseDto;
import com.foodtogo.auth.dto.SignupRequestDto;
import com.foodtogo.auth.jwt.JwtUtil;
import com.foodtogo.auth.users.UserRoleEnum;
import com.foodtogo.auth.users.Users;
import com.foodtogo.auth.users.UsersEntity;
import com.foodtogo.auth.users.UsersMapper;
import com.foodtogo.auth.users.UsersRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Slf4j(topic = "UsersService")
@Service
public class UsersService {
	private final UsersRepository usersRepository;
	private final PasswordEncoder passwordEncoder;
	private final JwtUtil jwtUtil;

	public UsersService(UsersRepository usersRepository, PasswordEncoder passwordEncoder, JwtUtil jwtUtil) {
		this.usersRepository = usersRepository;
		this.passwordEncoder = passwordEncoder;
		this.jwtUtil = jwtUtil;
	}

	// ADMIN_TOKEN
	private final String MANAGER_TOKEN = "AAABnvxRVklrnYxKZ0aHgTBcXukeZygoC";

	public LoginResponseDto login(LoginRequestDto requestDto, HttpServletResponse res) {
		String email = requestDto.getEmail();
		String password = requestDto.getPassword();

		// 사용자 확인
		final UsersEntity user = usersRepository.findByEmail(email).orElseThrow(
				() -> new IllegalArgumentException("등록된 이메일 없습니다.")
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

	public void signup(SignupRequestDto requestDto) {
		final String passwordEncode = passwordEncoder.encode(requestDto.getPassword());

		// email 중복확인
		final String email = requestDto.getEmail();
		Optional<UsersEntity> checkEmail = usersRepository.findByEmail(email);
		if (checkEmail.isPresent()) {
			throw new IllegalArgumentException("중복된 Email 입니다.");
		}

		// 사용자 ROLE 확인
		UserRoleEnum role = UserRoleEnum.CUSTOMER;
		if (requestDto.isAdmin()) {
			if (!MANAGER_TOKEN.equals(requestDto.getAdminToken())) {
				throw new IllegalArgumentException("매니저 암호가 틀려 등록이 불가능합니다.");
			}
			role = UserRoleEnum.MANAGER;
		}

		final Users user = UsersMapper.toDomain(requestDto, passwordEncode, role);

		// 도메인 객체를 엔티티로 변환
		UsersEntity userEntity = UsersMapper.toEntity(user);

		// 엔티티를 저장
		usersRepository.save(userEntity);
	}

	@Transactional(readOnly = true)
	public String findRoleByUserId(String userId) {
		return usersRepository.findRoleByUserId(UUID.fromString(userId)).orElseThrow(() -> new NoSuchElementException("User not found"));
	}
}
