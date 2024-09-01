//package com.foodtogo.auth.config;
//
//import jakarta.annotation.PostConstruct;
//import lombok.Getter;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.stereotype.Component;
//
//@Slf4j
//@Getter
//@Component
//public class EnvChecker {
//
//	@Value("${DB_URL}")
//	private String dbUrl;
//
//	@Value("${DB_USERNAME}")
//	private String dbUsername;
//
//	@Value("${DB_PASSWORD}")
//	private String dbPassword;
//
//	@Value("${REDIS_PASSWORD}")
//	private String redisPassword;
//
//
//	@Value("${spring.datasource.url}")
//	private String dbUrlFromApplication;
//
//	@Value("${spring.datasource.username}")
//	private String dbUsernameFromApplication;
//
//	@Value("${spring.datasource.password}")
//	private String dbPasswordFromApplication;
//
//	@Value("${spring.data.redis.password}")
//	private String redisPasswordFromApplication;
//
//
//	@PostConstruct
//	public void checkEnv() {
//
//		// spring run: vm option 확인
//		System.getProperty("DB_URL");
//		System.getProperty("DB_USERNAME");
//		System.getProperty("DB_PASSWORD");
//		System.getProperty("REDIS_PASSWORD");
//
//		log.debug("[option] DB_URL: " + dbUrl);
//		log.debug("[option] DB_USERNAME: " + dbUsername);
//		log.debug("[option] DB_PASSWORD: " + dbPassword);
//		log.debug("[option] REDIS_PASSWORD: " + redisPassword);
//
//		// application.yml에 직접 주입한 값 확인
//		log.debug("[yml] DB_URL: " + dbUrlFromApplication);
//		log.debug("[yml] DB_USERNAME: " + dbUsernameFromApplication);
//		log.debug("[yml] DB_PASSWORD: " + dbPasswordFromApplication);
//		log.debug("[yml] REDIS_PASSWORD: " + redisPasswordFromApplication);
//
//	}
//}