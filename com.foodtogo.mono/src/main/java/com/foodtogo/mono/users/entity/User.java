package com.foodtogo.mono.users.entity;

import com.foodtogo.mono.users.dto.request.UserUpdateRequestDto;
import com.foodtogo.mono.users.util.LogEntity;
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
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User extends LogEntity {

    @Id
    @UuidGenerator
    private UUID user_id;

    @Column(nullable = false, length = 100, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false, length = 100, unique = true)
    private String username;

    @Column(nullable = false, length = 100)
    private String phone_number;

    @Column(nullable = false, length = 100)
    private String nickname;

    @Column(nullable = false)
    private UserRoleEnum role;

    @Column(nullable = false)
    private Boolean is_public = true;

    @Column(nullable = false)
    private Boolean is_block = false;

    private String profile_url;

  
    // 회원 정보 수정 메소드
    public void updateUserInfo(UserUpdateRequestDto requestDto, String updatedBy) {
        this.nickname = requestDto.getNickname();
        this.is_public = requestDto.getIs_public();
        this.profile_url = requestDto.getProfile_url();
        setUpdatedBy(updatedBy);
    }
  
    public void deleteUser(String username) {
        this.setDeletedAt(LocalDateTime.now());
        this.setDeletedBy(username);
    }
}