package com.foodtogo.mono.user.core.domain;

import com.foodtogo.mono.address.core.domain.Address;
import com.foodtogo.mono.log.LogEntity;
import com.foodtogo.mono.user.core.enums.UserRoleEnum;
import com.foodtogo.mono.user.dto.request.SignupRequestDto;
import com.foodtogo.mono.user.dto.request.UpdateRequestDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "p_users")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends LogEntity {

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

    // Address와의 관계를 정의
    @OneToMany(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> addresses;

    @Builder
    private User(String email, String password, String username, String phoneNumber, String nickname, UserRoleEnum role, Boolean isPublic, Boolean isBlock, String profileUrl) {
        this.email = email;
        this.password = password;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.role = role;
        this.isPublic = isPublic;
        this.isBlock = isBlock;
        this.profileUrl = profileUrl;
    }

    public static User create(SignupRequestDto dto, String passwordEncode, UserRoleEnum role) {
        User user = User.builder()
                .email(dto.getEmail())
                .password(passwordEncode)
                .username(dto.getUsername())
                .phoneNumber(dto.getPhoneNumber())
                .nickname(dto.getNickname())
                .role(role)
                .profileUrl(dto.getProfileUrl())
                .isPublic(true)
                .isBlock(false)
                .build();
        user.setCreatedBy("system");// userId는 DB에서 생성됨
        return user;
    }

    public void updateUserInfo(UpdateRequestDto requestDto, String passwordEncode) {
        this.password = passwordEncode;
        this.username = requestDto.getUsername();
        this.phoneNumber = requestDto.getPhoneNumber();
        this.nickname = requestDto.getNickname();
        this.role = requestDto.getRole();
        this.profileUrl = requestDto.getProfileUrl();
    }

    public void delete(String deletedBy) {
        this.deletedBy = deletedBy;
        this.deletedAt = LocalDateTime.now();
    }

    public void changePassword(String password) {
        this.password = password;
    }

}
