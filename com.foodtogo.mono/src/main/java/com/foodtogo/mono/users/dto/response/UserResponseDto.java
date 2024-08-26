package com.foodtogo.mono.users.dto.response;

import com.foodtogo.mono.users.core.domain.User;
import com.foodtogo.mono.users.core.enums.UserRoleEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class UserResponseDto {
    private String email;
    private String username;
    private String phoneNumber;
    private String nickname;
    private UserRoleEnum role;
    private Boolean isPublic;
    private Boolean isBlock;
    private String profileUrl;

    public UserResponseDto(User user) {
        this.email = user.getEmail();
        this.username = user.getUsername();
        this.phoneNumber = user.getPhoneNumber();
        this.nickname = user.getNickname();
        this.role = user.getRole();
        this.isPublic = user.getIsPublic();
        this.isBlock = user.getIsBlock();
        this.profileUrl = user.getProfileUrl();
    }
}
