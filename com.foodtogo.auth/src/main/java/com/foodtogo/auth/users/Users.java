package com.foodtogo.auth.users;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
public class Users {

	private UUID userId;
	private String email;
	private String password;
	private String username;
	private String phoneNumber;
	private String nickname;
	private UserRoleEnum role;
	@Builder.Default
	private Boolean isPublic = true;
	@Builder.Default
	private Boolean isBlock = false;
	private String profileUrl;
	private LocalDateTime createdAt;
	private String createdBy;
	private LocalDateTime updatedAt;
	private String updatedBy;
	private LocalDateTime deletedAt;
	private String deletedBy;
}
