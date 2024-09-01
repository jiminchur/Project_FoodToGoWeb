package com.foodtogo.auth.controller;

import com.foodtogo.auth.jwt.JwtUtil;
import com.foodtogo.auth.user.core.enums.UserRoleEnum;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Slf4j(topic = "AuthController")
@RestController
@RequestMapping("/auth/v1")
public class AuthController {

	public static final String AUTHORIZATION_HEADER = "Authorization";
	public static final String ROLE_KEY = "role";
	private final JwtUtil jwtUtil;

	public AuthController(JwtUtil jwtUtil) {
		this.jwtUtil = jwtUtil;
	}

	@GetMapping("/create-cookie")
	public String createCookie(HttpServletResponse res) {
		addCookie("Robbie Auth", res);

		return "createCookie";
	}

	@GetMapping("/get-cookie")
	public String getCookie(@CookieValue(AUTHORIZATION_HEADER) String value) {
		log.debug("value = " + value);

		return "getCookie : " + value;
	}

	public void addCookie(String cookieValue, HttpServletResponse res) {
		cookieValue = URLEncoder.encode(cookieValue, StandardCharsets.UTF_8).replace("\\+", "%20"); // Cookie Value 에는 공백이 불가능해서 encoding 진행

		Cookie cookie = new Cookie(AUTHORIZATION_HEADER, cookieValue); // Name-Value
		cookie.setPath("/");
		cookie.setMaxAge(30 * 60);

		// Response 객체에 Cookie 추가
		res.addCookie(cookie);
	}

	@GetMapping("/create-session")
	public String createSession(HttpServletRequest req) {
		// 세션이 존재할 경우 세션 반환, 없을 경우 새로운 세션을 생성한 후 반환
		HttpSession session = req.getSession(true);

		// 세션에 저장될 정보 Name - Value 를 추가합니다.
		session.setAttribute(AUTHORIZATION_HEADER, "Robbie Auth");

		return "createSession";
	}

	@GetMapping("/get-session")
	public String getSession(HttpServletRequest req) {
		// 세션이 존재할 경우 세션 반환, 없을 경우 null 반환
		HttpSession session = req.getSession(false);

		String value = (String) session.getAttribute(AUTHORIZATION_HEADER); // 가져온 세션에 저장된 Value 를 Name 을 사용하여 가져옵니다.

		log.debug("value = " + value);


		return "getSession : " + value;
	}

	// jwt
	@GetMapping("/create-jwt")
	public String createJwt(HttpServletResponse res) {
		// Jwt 생성
		String token = jwtUtil.createToken("RobbieID", UserRoleEnum.CUSTOMER);

		// Jwt 쿠키 저장
		jwtUtil.addJwtToCookie(token, res);

		return "createJwt : " + token;
	}

	@GetMapping("/get-jwt")
	public String getJwt(@CookieValue(AUTHORIZATION_HEADER) String tokenValue) {

		// JWT 토큰 substring
		final String token = jwtUtil.extractTokenFromBearer(tokenValue);

		// 토큰 검증
		if (!jwtUtil.validateToken(token)) {
			throw new IllegalArgumentException("Token Error");
		}

		// 토큰에서 사용자 정보 가져오기
		Claims info = jwtUtil.getUserInfoFromToken(token);
		// 사용자 username
		String userId = info.getSubject();
		log.debug("userId = " + userId);
		// 사용자 권한
		String roleString = (String) info.get(ROLE_KEY);
		UserRoleEnum userRole = UserRoleEnum.getRoleEnum(roleString);
		log.debug("authority = " + userRole);

		return "getJwt : " + userId + ", " + userRole;
	}

}