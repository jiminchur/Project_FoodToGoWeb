package com.foodtogo.mono.users.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRequestDto {
    private String email;
    private String password;
    private String username;
    private String phoneNumber;
    private String nickname;
}