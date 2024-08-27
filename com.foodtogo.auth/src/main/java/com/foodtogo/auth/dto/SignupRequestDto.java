package com.foodtogo.auth.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SignupRequestDto {
	@NotBlank(message = "Email is required")
	@Email(message = "Email should be valid")
	private String email;

	@NotBlank(message = "Password is required")
	@Pattern(regexp = "^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,15}$", message = "Password must be 8-15 characters long, and contain at least one letter, one number, and one special character.")
	private String password;

	@NotBlank(message = "Username is required")
	@Pattern(regexp = "^[a-zA-Z0-9가-힣_-]{4,10}$", message = "Username must be 4-10 characters long, and contain only lowercase letters and numbers.")
	private String username;

	private String phoneNumber;

	private String nickname;

	@NotNull(message = "Role is required.")
	private String role;

	private String profileUrl;

	private boolean admin = false;
	private String adminToken = ""; // 관리자 토큰은 선택적일 수 있습니다.
	private boolean isBlock = false; // 기본값 false로 설정
}