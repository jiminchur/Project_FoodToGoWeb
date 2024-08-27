package com.foodtogo.mono.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDto {
    private String nickname;
    private Boolean isPublic;
    private String profileUrl;
}