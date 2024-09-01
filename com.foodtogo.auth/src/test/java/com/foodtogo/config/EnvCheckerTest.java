//package com.foodtogo.config;
//
//import com.foodtogo.auth.config.EnvChecker;
//import lombok.extern.slf4j.Slf4j;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.test.context.ActiveProfiles;
//
//@Slf4j
//@SpringBootTest(classes = EnvChecker.class)
//@ActiveProfiles("dev")
//public class EnvCheckerTest {
//
//	@Autowired
//	private EnvChecker envChecker;
//
//	@Test
//	public void testEnvProperties() {
//		// Validate system properties
//		String expectedDbUrl = System.getProperty("DB_URL");
//		String expectedDbUsername = System.getProperty("DB_USERNAME");
//		String expectedDbPassword = System.getProperty("DB_PASSWORD");
//		String expectedRedisPassword = System.getProperty("REDIS_PASSWORD");
//
//		// Validate application.yml properties
//		String dbUrlFromApplication = envChecker.getDbUrlFromApplication();
//		String dbUsernameFromApplication = envChecker.getDbUsernameFromApplication();
//		String dbPasswordFromApplication = envChecker.getDbPasswordFromApplication();
//		String redisPasswordFromApplication = envChecker.getRedisPasswordFromApplication();
//
//
//		log.info("[option] DB_URL: " + expectedDbUrl);
//		log.info("[option] DB_USERNAME: " + expectedDbUsername);
//		log.info("[option] DB_PASSWORD: " + expectedDbPassword);
//		log.info("[option] REDIS_PASSWORD: " + expectedRedisPassword);
//
//		// application.yml에 직접 주입한 값 확인
//		log.info("[yml] DB_URL: " + dbUrlFromApplication);
//		log.info("[yml] DB_USERNAME: " + dbUsernameFromApplication);
//		log.info("[yml] DB_PASSWORD: " + dbPasswordFromApplication);
//		log.info("[yml] REDIS_PASSWORD: " + redisPasswordFromApplication);
//	}
//}
