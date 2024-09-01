package com.foodtogo.auth.user.core.enums;

import com.foodtogo.auth.error.CustomAccessDeniedException;
import lombok.Getter;

@Getter
public enum UserRoleEnum {
    CUSTOMER(Authority.CUSTOMER),
    OWNER(Authority.OWNER),
    MANAGER(Authority.MANAGER),
    MASTER(Authority.MASTER);

    private final String authority;

    UserRoleEnum(String authority) {
        this.authority = authority;
    }


    public static UserRoleEnum getRoleEnum(String s) {
        for (UserRoleEnum role : values()) {
            if (s.equals(role.authority)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid authority: " + s);
    }

    /**
     * 주어진 권한이 MANAGER 또는 MASTER인지 확인합니다.
     *
     * @param s 권한 문자열
     * @return true if the authority is MANAGER or MASTER; false otherwise
     */
    public static boolean isManagerOrMaster(String s) {
        return MANAGER.authority.equals(s) || MASTER.authority.equals(s);
    }


    /**
     * MANAGER 또는 MASTER 가 아니면 권한 접근 에외를 발생시킵니다.
     *
     * @param role
     */
    public static void validateManagerOrMaster(String role) {
        if (!isManagerOrMaster(role)) {
            throw new CustomAccessDeniedException("Access denied. User role is not MANAGER or MASTER.");
        }
    }
}