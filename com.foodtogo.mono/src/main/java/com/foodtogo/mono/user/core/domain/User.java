package com.foodtogo.mono.user.core.domain;

import com.foodtogo.mono.log.LogEntity;
import com.foodtogo.mono.user.core.enums.UserRoleEnum;
import com.foodtogo.mono.user.dto.request.SignupRequestDto;
import com.foodtogo.mono.user.dto.request.UserUpdateRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_users")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User extends LogEntity {

    @Id
    @UuidGenerator
    private UUID userId;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String phoneNumber;

    @Column(nullable = false, length = 100)
    private String nickname;

    @Column(nullable = false)
    private UserRoleEnum role;

    @Column(nullable = false)
    private Boolean isPublic = true;

    @Column(nullable = false)
    private Boolean isBlock = false;

    private String profileUrl;


    // 회원 가입
    public User(SignupRequestDto signupRequestDto, String encodedPassword) {
        this.email = signupRequestDto.getEmail();
        this.password = encodedPassword;
        this.username = signupRequestDto.getUsername();
        this.phoneNumber = signupRequestDto.getPhoneNumber();
        this.nickname = signupRequestDto.getNickname();
        if (signupRequestDto.getIsMaster()) {
            this.role = UserRoleEnum.MASTER;
        } else if (signupRequestDto.getIsManager()) {
            this.role = UserRoleEnum.MANAGER;
        } else if (signupRequestDto.getIsOwner()) {
            this.role = UserRoleEnum.OWNER;
        } else {
            this.role = UserRoleEnum.CUSTOMER;
        }
        this.createdBy = signupRequestDto.getUsername();
    }

    // 회원 정보 수정 메소드
    public void updateUserInfo(UserUpdateRequestDto requestDto, String updatedBy) {
        this.nickname = requestDto.getNickname();
        this.isPublic = requestDto.getIsPublic();
        this.profileUrl = requestDto.getProfileUrl();
        this.updatedBy = updatedBy;
    }

    // 회원 삭제
    public void deleteUser(String deletedBy) {
        this.deletedAt = LocalDateTime.now();
        this.deletedBy = deletedBy;
        this.isBlock = true;
    }
}
