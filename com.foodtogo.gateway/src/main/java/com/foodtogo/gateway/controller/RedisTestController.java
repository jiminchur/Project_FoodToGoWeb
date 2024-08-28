package com.foodtogo.gateway.controller;

import com.foodtogo.gateway.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.util.Optional;

@Slf4j(topic = "RedisTestController")
@RestController
public class RedisTestController {

	private final RedisService redisService;

	public RedisTestController(RedisService redisService) {
		this.redisService = redisService;
	}

	@GetMapping("/test/blacklist")
	public ResponseEntity<Boolean> isTokenBlacklisted(@RequestParam String token) {
		return ResponseEntity.ok(redisService.isBlacklistedToken(token));
	}

	@GetMapping("/test/role")
	public ResponseEntity<String> getRole(@RequestParam String userId, @RequestParam String role) {
		try {
			Optional<String> roleFromRedis = redisService.getRoleFromRedis(userId, role);
			if (roleFromRedis.isPresent()) {
				return ResponseEntity.ok(roleFromRedis.get());
			}

			log.error("Role mismatch for userId {}: role: {}", userId, role);
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Role mismatch for userId: " + userId + ", role: " + role);
		} catch (Exception e) {
			log.error("Error fetching role from Redis: ", e);
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}