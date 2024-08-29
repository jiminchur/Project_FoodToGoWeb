package com.foodtogo.gateway.users;

import lombok.Getter;

import java.util.Arrays;

@Getter
public enum UserRoleEnum {
	CUSTOMER(Authority.CUSTOMER), OWNER(Authority.OWNER), MANAGER(Authority.MANAGER), MASTER(Authority.MASTER);
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

	public static boolean isValidRole(String role) {
		return Arrays.stream(values())
				.map(UserRoleEnum::getAuthority)
				.anyMatch(authority -> authority.equals(role));
	}


}
