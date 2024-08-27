package com.foodtogo.auth.users;

import com.foodtogo.auth.dto.SignupRequestDto;

import java.util.UUID;

public class UsersMapper {
	private UsersMapper() {
	}

	public static Users toDomain(UsersEntity entity) {
		return Users.builder()
				.userId(entity.getUserId())
				.email(entity.getEmail())
				.password(entity.getPassword())
				.username(entity.getUsername())
				.phoneNumber(entity.getPhoneNumber())
				.nickname(entity.getNickname())
				.role(entity.getRole())
				.isPublic(entity.getIsPublic())
				.isBlock(entity.getIsBlock())
				.profileUrl(entity.getProfileUrl())
				.createdAt(entity.getCreatedAt())
				.createdBy(entity.getCreatedBy())
				.updatedAt(entity.getUpdatedAt())
				.updatedBy(entity.getUpdatedBy())
				.deletedAt(entity.getDeletedAt())
				.deletedBy(entity.getDeletedBy())
				.build();
	}

	public static UsersEntity toEntity(Users domain) {
		UsersEntity entity = new UsersEntity();
		entity.setUserId(domain.getUserId());// UUID 객체를 직접 사용
		entity.setEmail(domain.getEmail());
		entity.setPassword(domain.getPassword());
		entity.setUsername(domain.getUsername());
		entity.setPhoneNumber(domain.getPhoneNumber());
		entity.setNickname(domain.getNickname());
		entity.setRole(domain.getRole());
		entity.setIsPublic(domain.getIsPublic());
		entity.setIsBlock(domain.getIsBlock());
		entity.setProfileUrl(domain.getProfileUrl());
		entity.setCreatedAt(domain.getCreatedAt());
		entity.setCreatedBy(domain.getCreatedBy());
		entity.setUpdatedAt(domain.getUpdatedAt());
		entity.setUpdatedBy(domain.getUpdatedBy());
		entity.setDeletedAt(domain.getDeletedAt());
		entity.setDeletedBy(domain.getDeletedBy());
		return entity;
	}

	public static Users toDomain(SignupRequestDto dto, String encodedPassword, UserRoleEnum role) {
		return Users.builder()
				.userId(UUID.randomUUID())
				.email(dto.getEmail())
				.password(encodedPassword)
				.username(dto.getUsername())
				.phoneNumber(dto.getPhoneNumber())
				.nickname(dto.getNickname())
				.role(role)
				.profileUrl(dto.getProfileUrl())
				.isBlock(dto.isBlock())
				.isPublic(true)
				.createdBy("system")
				.build();
	}
}
