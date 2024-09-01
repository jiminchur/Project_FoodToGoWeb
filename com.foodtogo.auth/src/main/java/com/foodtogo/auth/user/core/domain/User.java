package com.foodtogo.auth.user.core.domain;

import com.foodtogo.auth.dto.request.SignupRequestDto;
import com.foodtogo.auth.user.core.enums.UserRoleEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {
    @Id
    @UuidGenerator
    @Column(name = "user_id", columnDefinition = "UUID", nullable = false, unique = true)
    private UUID userId;

    @Email
    @Column(name = "email", length = 255, nullable = false, unique = true)
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

    @Column(name = "created_by", nullable = false, updatable = false, length = 100)
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
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @Builder
    private User(String email, String password, String username, String phoneNumber, String nickname, UserRoleEnum role, Boolean isPublic, Boolean isBlock, String profileUrl, String createdBy) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.role = role;
        this.isPublic = isPublic;
        this.isBlock = isBlock;
        this.profileUrl = profileUrl;
        this.createdBy = createdBy;
    }

    public static User createUser(SignupRequestDto dto, String passwordEncode, UserRoleEnum role) {
        return User.builder()
                .email(dto.getEmail())
                .password(passwordEncode)
                .username(dto.getUsername())
                .phoneNumber(dto.getPhoneNumber())
                .nickname(dto.getNickname())
                .role(role)
                .profileUrl(dto.getProfileUrl())
                .isPublic(true)
                .isBlock(false)
                .createdBy("system")// userId는 DB에서 생성됨
                .build();
    }
}