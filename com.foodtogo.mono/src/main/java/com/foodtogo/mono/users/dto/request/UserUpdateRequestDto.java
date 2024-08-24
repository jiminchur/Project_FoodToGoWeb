package com.foodtogo.mono.users.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateRequestDto {
    private String nickname;
    private Boolean is_public;
    private String profile_url;
}