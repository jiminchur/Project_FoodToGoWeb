package com.foodtogo.mono.restaurant_category.controller;

import com.foodtogo.mono.restaurant_category.core.RestaurantCategory;
import com.foodtogo.mono.restaurant_category.dto.RestaurantCategoryRequestDto;
import com.foodtogo.mono.restaurant_category.dto.RestaurantCategoryResponseDto;
import com.foodtogo.mono.restaurant_category.service.RestaurantCategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
@Slf4j
public class RestaurantCategoryController {

    private final RestaurantCategoryService restaurantCategoryService;

    // 카테고리 등록
    @PostMapping
    public RestaurantCategoryResponseDto createCategories(
            @RequestBody RestaurantCategoryRequestDto restaurantCategoryRequestDto,
            @RequestHeader(value = "X-User-Id", required = true) String userId,
            @RequestHeader(value = "X-Role", required = true) String role) {

        validateRole(role);
        log.info("사용자 {}에 대한 카테고리 생성 중", userId);

        return restaurantCategoryService.createCategories(restaurantCategoryRequestDto, userId);
    }

    // 카테고리 목록 조회
    @GetMapping
    public List<RestaurantCategory> getAllCategories() {
        log.info("모든 카테고리 조회 중");
        return restaurantCategoryService.getAllCategories();
    }

    // 카테고리 수정
    @PutMapping("/{category_id}")
    public RestaurantCategoryResponseDto updateCategories(
            @PathVariable("category_id") UUID categoryId,
            @RequestBody RestaurantCategoryRequestDto restaurantCategoryRequestDto,
            @RequestHeader(value = "X-User-Id", required = true) String userId,
            @RequestHeader(value = "X-Role", required = true) String role) {

        validateRole(role);
        log.info("사용자 {}에 대한 ID {} 카테고리 수정 중", userId, categoryId);

        return restaurantCategoryService.updateCategories(categoryId, restaurantCategoryRequestDto, userId);
    }

    // 카테고리 삭제
    @DeleteMapping("/{category_id}")
    public void deleteCategory(
            @PathVariable("category_id") UUID categoryId,
            @RequestHeader(value = "X-User-Id", required = true) String userId,
            @RequestHeader(value = "X-Role", required = true) String role) {

        validateRole(role);
        log.info("사용자 {}에 대한 ID {} 카테고리 삭제 중", userId, categoryId);

        restaurantCategoryService.deleteCategory(categoryId, userId);
    }

    // 역할 검증
    private void validateRole(String role) {
        if (!"MASTER".equals(role) && !"MANAGER".equals(role)) {
            log.warn("Access denied. User role: {}", role);
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. User role is not MANAGER or OWNER.");
        }
    }
}
