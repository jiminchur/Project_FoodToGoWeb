package com.foodtogo.auth.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.CacheKeyPrefix;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.RedisSerializationContext;
import org.springframework.data.redis.serializer.RedisSerializer;

import java.time.Duration;

@Configuration
@EnableCaching
public class CacheConfig {

	@Bean
	public RedisCacheManager redisCacheManager(RedisConnectionFactory redisConnectionFactory) {
		RedisCacheConfiguration configuration = RedisCacheConfiguration
				.defaultCacheConfig()
				.disableCachingNullValues()
				.entryTtl(Duration.ofHours(1))
				// 캐시를 구분하는 접두사 설정
				.computePrefixWith(CacheKeyPrefix.simple())

				//캐시에 저장할 값을 직렬화 할지
				.serializeValuesWith(
						RedisSerializationContext.SerializationPair.fromSerializer(RedisSerializer.java())
				);
		return RedisCacheManager
				.builder(redisConnectionFactory)
				.cacheDefaults(configuration)
				.build();
	}
}
