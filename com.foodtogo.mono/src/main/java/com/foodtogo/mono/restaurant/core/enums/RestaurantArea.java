package com.foodtogo.mono.restaurant.core.enums;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum RestaurantArea {
    광화문(Authority.광화문);

    private final String authority;

    public static class Authority {
        public static final String 광화문 = "광화문 지역";
    }
}
