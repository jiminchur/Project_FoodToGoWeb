package com.foodtogo.mono.user.dto.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchDto {
    private String keyword; // 회원 검색 키워드
    private String sortBy;  // 정렬 기준 (createdAt, updatedAt 등)
    private int page;       // 페이지 번호
    private int size;       // 페이지 크기

    // 페이지 크기 유효성 검사를 수행하고, 기본값을 반환하는 메서드
    public int getValidatedSize() {
        return (size == 10 || size == 30 || size == 50) ? size : 10;
    }
}