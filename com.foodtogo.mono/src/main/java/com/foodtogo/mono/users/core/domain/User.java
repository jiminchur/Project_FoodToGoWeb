package com.foodtogo.mono.users.core.domain;

import com.foodtogo.mono.log.LogEntity;
import com.foodtogo.mono.users.core.enums.UserRoleEnum;
import com.foodtogo.mono.users.dto.request.UserUpdateRequestDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_users")
@Getter
@Setter
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


    // 회원 정보 수정 메소드
    public void updateUserInfo(UserUpdateRequestDto requestDto, String updatedBy) {
        this.nickname = requestDto.getNickname();
        this.isPublic = requestDto.getIsPublic();
        this.profileUrl = requestDto.getProfileUrl();
        setUpdatedBy(updatedBy);
    }

    public void deleteUser(String username) {
        this.setDeletedAt(LocalDateTime.now());
        this.setDeletedBy(username);
    }
}
