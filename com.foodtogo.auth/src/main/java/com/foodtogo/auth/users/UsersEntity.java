package com.foodtogo.auth.users;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_users")
@Data
@NoArgsConstructor
public class UsersEntity {
	@Id
	@Column(name = "user_id", columnDefinition = "UUID", nullable = false, unique = true)
	private UUID userId;

	@Column(name = "email", length = 255, nullable = false, unique = true)
	@Email
	private String email;

	@Column(name = "password", length = 255, nullable = false)
	private String password;

	@Column(name = "username", length = 100, nullable = false)
	private String username;

	@Column(name = "phone_number", length = 20)
	private String phoneNumber;

	@Column(name = "nickname", length = 100, nullable = false, unique = true)
	private String nickname;

	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false)
	private UserRoleEnum role;

	@Column(name = "is_public", nullable = false)
	private Boolean isPublic = true;

	@Column(name = "is_block", nullable = false)
	private Boolean isBlock = false;

	@Column(name = "profile_url", columnDefinition = "TEXT")
	private String profileUrl;

	@Column(name = "created_at", nullable = false, updatable = false)
	private LocalDateTime createdAt = LocalDateTime.now();

	@Column(name = "created_by", length = 100)
	private String createdBy;

	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@Column(name = "updated_by", length = 100)
	private String updatedBy;

	@Column(name = "deleted_at")
	private LocalDateTime deletedAt;

	@Column(name = "deleted_by", length = 100)
	private String deletedBy;

	@PrePersist
	public void prePersist() {
		this.createdAt = LocalDateTime.now();

		// UUID 생성
		if (this.userId == null) {
			this.userId = UUID.randomUUID();
		}
	}

	@PreUpdate
	public void preUpdate() {
		this.updatedAt = LocalDateTime.now();
	}
}