package com.foodtogo.auth.dto;

import com.foodtogo.auth.users.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResponseDto {
	private String token;
	private UUID userId;
	private UserRoleEnum role;
}
