package com.foodtogo.gateway.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class HeaderResponseDto {
	private String token;
	private String userId;
	private String role;
}
