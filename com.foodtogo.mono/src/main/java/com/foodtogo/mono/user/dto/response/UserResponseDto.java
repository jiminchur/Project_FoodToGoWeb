package com.foodtogo.mono.user.dto.response;

import com.foodtogo.mono.user.core.domain.User;
import com.foodtogo.mono.user.core.enums.UserRoleEnum;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class UserResponseDto {
    private String email;
    private String username;
    private String phoneNumber;
    private String nickname;
    private UserRoleEnum role;
    private Boolean isPublic;
    private Boolean isBlock;
    private String profileUrl;

    // private 생성자, 외부에서 객체 생성을 막음
    private UserResponseDto(String email, String username, String phoneNumber, String nickname, UserRoleEnum role, Boolean isPublic, Boolean isBlock, String profileUrl) {
        this.email = email;
        this.username = username;
        this.phoneNumber = phoneNumber;
        this.nickname = nickname;
        this.role = role;
        this.isPublic = isPublic;
        this.isBlock = isBlock;
        this.profileUrl = profileUrl;
    }

    /**
     * 오로지 하나의 객체만 반환하도록 하여 객체를 재사용해 메모리를 아끼도록 유도
     * @param user
     * @return
     */
    public static UserResponseDto fromUser(User user) {
        return new UserResponseDto(
                user.getEmail(),
                user.getUsername(),
                user.getPhoneNumber(),
                user.getNickname(),
                user.getRole(),
                user.getIsPublic(),
                user.getIsBlock(),
                user.getProfileUrl()
        );
    }
}
