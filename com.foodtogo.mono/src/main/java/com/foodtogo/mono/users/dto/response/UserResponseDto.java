package com.foodtogo.mono.users.dto.response;

import com.foodtogo.mono.users.entity.User;
import com.foodtogo.mono.users.entity.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String email;
    private String username;
    private String phone_number;
    private String nickname;
    private UserRoleEnum role;
    private Boolean is_public;
    private Boolean is_block;
    private String profile_url;

    public UserResponseDto(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.phone_number = user.getPhone_number();
        this.nickname = user.getNickname();
        this.role = user.getRole();
        this.is_public = user.getIs_public();
        this.is_block = user.getIs_block();
        this.profile_url = user.getProfile_url();
    }
}
