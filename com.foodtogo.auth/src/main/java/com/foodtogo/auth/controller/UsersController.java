package com.foodtogo.auth.controller;

import com.foodtogo.auth.dto.LoginRequestDto;
import com.foodtogo.auth.dto.LoginResponseDto;
import com.foodtogo.auth.dto.SignupRequestDto;
import com.foodtogo.auth.jwt.JwtUtil;
import com.foodtogo.auth.service.RedisService;
import com.foodtogo.auth.service.UsersService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j(topic = "UsersController")
@RestController
@RequestMapping("/auth/v1/users")
public class UsersController {

	private final UsersService usersService;
	private final RedisService redisService;
	private final JwtUtil jwtUtil;

	public UsersController(UsersService usersService, RedisService redisService, JwtUtil jwtUtil) {
		this.usersService = usersService;
		this.redisService = redisService;
		this.jwtUtil = jwtUtil;
	}

	/*
			jwt 토큰이 없는 상태
				1. 로그인 요청
				- email과 비밀번호가 일치하는지 확인
				- 일치하면 토큰을 발행한다
				- 토큰을 쿠키에 담는다, 게이트웨이 서버로 보낸다, 게이트웨이 서버는 유저에게 jwt 토큰을 준다
			*/
	@PostMapping("/login")
	public ResponseEntity<LoginResponseDto> login(@RequestBody LoginRequestDto requestDto, HttpServletResponse res) {
		LoginResponseDto responseDto = usersService.login(requestDto, res);
		// redis role 추가
		redisService.updateUserRole(responseDto.getUserId().toString(), responseDto.getRole());
		return ResponseEntity.ok(responseDto);
	}

	/*
	5. 로그아웃
		- 레디스에 블랙리스트jwt 을 넣는다
		- 세션은 안쓰니 세션 제거는 필요없다
		- 클라이언트에서 jwt 토큰 쿠키 삭제
	 */
	@PostMapping("/logout")
	public ResponseEntity<Void> logout(
			@RequestHeader("Authorization") String tokenValue
			, @RequestHeader(value = "X-Token", required = true) String extractedToken
			, @RequestHeader(value = "X-User-Id", required = true) String userId
			, @RequestHeader(value = "X-Role", required = true) String role
			, HttpServletResponse res

	) {
		// 토큰을 블랙리스트에 추가
		redisService.addBlackToken(extractedToken);
		return ResponseEntity.ok().build();
	}

	/*
			jwt 토큰이 없는 상태
				2. 회원가입
					- 게이트웨이 서버에서 모노리틱서버로 유저를 생성한다,
					- 모노리틱 서버에서 인증서버로 redis 요청을 한다.
					- 인증서버에서는 모노리틱서버에서 요청한 userId와 role을 추가한다
						- PUT	/auth/v1/cache/users/{user_id}
						- 레디스 (userid:권한) 이때 처음 추가
	*/

	@PostMapping("/signup")
	public String signup(@RequestBody @Valid SignupRequestDto requestDto, BindingResult bindingResult) {
		// validation 예외 처리
		List<FieldError> fieldErrors = bindingResult.getFieldErrors();
		if (!fieldErrors.isEmpty()) {
			for (FieldError fieldError : bindingResult.getFieldErrors()) {
				log.error(fieldError.getField() + fieldError.getDefaultMessage());
			}
			return "validation error";
		}

		usersService.signup(requestDto);
		return "signup";
	}

}
