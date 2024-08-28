package com.foodtogo.gateway.redis;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.foodtogo.gateway.service.AuthService;
import com.foodtogo.gateway.service.RedisService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import reactor.core.publisher.Mono;

class RedisServiceTest {

	private RedisService redisService;
	private RedisTemplate<String, Object> redisTemplate;
	private ValueOperations<String, Object> valueOperations;
	private AuthService authService;

	@BeforeEach
	void setUp() {
		redisTemplate = mock(RedisTemplate.class);
		valueOperations = mock(ValueOperations.class);
		authService = mock(AuthService.class);

		when(redisTemplate.opsForValue()).thenReturn(valueOperations);

		redisService = new RedisService(redisTemplate, authService);
	}

	@Test
	void testIsBlacklistedToken() {
		// given
		String token = "dummyToken";
		when(redisTemplate.hasKey(anyString())).thenReturn(true);

		// when
		boolean result = redisService.isBlacklistedToken(token);

		// then
		assertTrue(result);
		verify(redisTemplate).hasKey("blacklist:" + token);
	}

	@Test
	void testGetRoleFromRedis() {
		// given
		String userId = "user1";
		String role = "USER";
		when(valueOperations.get(anyString())).thenReturn(null);
		when(authService.getRoleFromAuthService(anyString())).thenReturn(Mono.just(role));

		// when
		Mono<String> result = redisService.getRoleFromRedisMono(userId, role);

		// then
		assertEquals(role, result);
		verify(valueOperations).get("user:role:" + userId);
		verify(authService).getRoleFromAuthService(userId);
	}
}