package com.foodtogo.auth.users;

public final class Authority {
	public static final String CUSTOMER = "CUSTOMER";
	public static final String OWNER = "OWNER";
	public static final String MANAGER = "MANAGER";
	public static final String MASTER = "MASTER";

	private Authority() {
		// 상수만 제공하므로 인스턴스 생성을 방지합니다.
	}
}
