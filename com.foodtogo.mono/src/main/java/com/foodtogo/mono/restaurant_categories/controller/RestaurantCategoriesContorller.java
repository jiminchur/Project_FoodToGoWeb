package com.foodtogo.mono.restaurant_categories.controller;

import com.foodtogo.mono.restaurant_categories.core.RestaurantCategories;
import com.foodtogo.mono.restaurant_categories.dto.RestaurantCategoriesRequestDto;
import com.foodtogo.mono.restaurant_categories.dto.RestaurantCategoriesResponseDto;
import com.foodtogo.mono.restaurant_categories.service.RestaurantCategoriesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class RestaurantCategoriesContorller {

    private final RestaurantCategoriesService restaurantCategoriesService;

    // 카테고리 등록
    @PostMapping
    public RestaurantCategoriesResponseDto createCategories(
            @RequestBody RestaurantCategoriesRequestDto restaurantCategoriesRequestDto,
            @RequestHeader(value = "X-User-Id", required = true) String userId
    ){
        return restaurantCategoriesService.createCategories(restaurantCategoriesRequestDto,userId);
    }

    // 카테고리 목록 조회
    @GetMapping
    public List<RestaurantCategories> getAllCategories(){
        return restaurantCategoriesService.getAllCategories();
    }

    // 카테고리 수정
    @PutMapping("/{category_id}")
    public RestaurantCategoriesResponseDto updateCategories(
            @PathVariable("category_id") UUID categoryId,
            @RequestBody RestaurantCategoriesRequestDto restaurantCategoriesRequestDto,
            @RequestHeader(value = "X-User-Id", required = true) String userId
    ){
        return restaurantCategoriesService.updateCategories(categoryId,restaurantCategoriesRequestDto,userId);
    }

    // 카테고리 삭제
}
