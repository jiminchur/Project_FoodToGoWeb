package com.foodtogo.mono.common.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDto {
    private String keyword; // 회원 검색 키워드
    private String sortBy;  // 정렬 기준 (createdAt, updatedAt 등)
    private Integer page;       // 페이지 번호
    private Integer size;       // 페이지 크기

    // 페이지 크기 유효성 검사를 수행하고, 기본값을 반환하는 메서드
    public int getValidatedSize() {
        return (size != null && (size == 10 || size == 30 || size == 50)) ? size : 10;
    }

    // 페이지 번호 기본값 설정 메서드
    public int getValidatedPage() {
        return (page != null && page >= 0) ? page : 0;
    }

    // 정렬 기준 기본값 설정 메서드
    public String getValidatedSortBy() {
        return (sortBy != null && !sortBy.isEmpty()) ? sortBy : "createdAt";
    }
}