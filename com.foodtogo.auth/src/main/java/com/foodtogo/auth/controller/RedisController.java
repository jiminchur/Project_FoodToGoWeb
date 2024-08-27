package com.foodtogo.auth.controller;

import com.foodtogo.auth.dto.JwtTokenRequestDto;
import com.foodtogo.auth.dto.UserRoleRequestDto;
import com.foodtogo.auth.service.RedisService;
import com.foodtogo.auth.users.UserRoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j(topic = "RedisController")
@RestController
@RequestMapping("/auth/v1/cache")
public class RedisController {

	private final RedisService redisService;

	public RedisController(RedisService redisService) {
		this.redisService = redisService;
	}
	/*
			jwt 토큰이 없는 상태
				2. 회원가입
					- 게이트웨이 서버에서 모노리틱서버로 유저를 생성한다,
					- 모노리틱 서버에서 인증서버로 redis 요청을 한다.
					- 인증서버에서는 모노리틱서버에서 요청한 userId와 role을 추가한다
						- PUT	/auth/v1/cache/users/{userId}/role
						- 레디스 (userid:권한) 이때 처음 추가
	*/

	/**
	 * Redis 캐시 조회 및 갱신
	 * 권한 조회함
	 *
	 * @return 권한명
	 */
	@GetMapping("/users/{userId}/role")
	public ResponseEntity<UserRoleEnum> getRole(@PathVariable("userId") String userId) {
		log.info("Fetching role for user_id: {}", userId);
		UserRoleEnum role = redisService.getUserRole(userId);
		return ResponseEntity.ok(role);
	}

	/**
	 * Redis 캐시 업데이트 (사용자 권한, 정보 업데이트)
	 * role체크 안하고 바로 업데이트 함
	 *
	 * @return 권한명
	 */
	@PostMapping("/users/{userId}/role")
	public ResponseEntity<String> updateRole(@PathVariable("userId") String userId, @RequestBody UserRoleRequestDto requestDto) {
		try {
			log.info("Updating role for user_id: {}", userId);
			// 앞에서 role 검사가 끝났다는 전제하에
			redisService.updateUserRole(userId, UserRoleEnum.getRoleEnum(requestDto.getNewRole()));
			return ResponseEntity.ok("User role updated successfully.");
		} catch (IllegalArgumentException e) {
			log.error("Invalid role: {}", requestDto.getNewRole(), e);
			return ResponseEntity.badRequest().body(e.toString());
		} catch (Exception e) {
			return ResponseEntity.internalServerError().body(e.toString());
		}
	}

	/**
	 * Redis 캐시 삭제 (사용자 권한, 정보 삭제)
	 *
	 * @param userId 사용자 ID
	 * @return 성공 메시지
	 */
	@DeleteMapping("/users/{userId}/role")
	public ResponseEntity<String> deleteRole(@PathVariable("userId") String userId) {
		log.info("Deleting role cache for user_id: {}", userId);
		redisService.deleteUserRole(userId);
		return ResponseEntity.ok("User role cache deleted successfully.");
	}

	/**
	 * 로그아웃 요청 시, jwt 재발급시
	 * 현재 레디스에 블랙리스트 JWT 추가
	 *
	 * @param token 블랙리스트에 추가할 JWT 토큰
	 * @return 성공 메시지
	 */
	@PostMapping("/blacklist/tokens")
	public ResponseEntity<String> addBlackToken(@RequestBody JwtTokenRequestDto requestDto) {
		log.info("Adding token to blacklist: {}", requestDto.getToken());
		redisService.addBlackToken(requestDto.getToken());
		return ResponseEntity.ok("Token added to blacklist.");
	}
}
