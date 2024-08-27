package com.foodtogo.mono.restaurant_category.controller;

import com.foodtogo.mono.restaurant_category.core.RestaurantCategory;
import com.foodtogo.mono.restaurant_category.dto.RestaurantCategoriesRequestDto;
import com.foodtogo.mono.restaurant_category.dto.RestaurantCategoryResponseDto;
import com.foodtogo.mono.restaurant_category.service.RestaurantCategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@RequiredArgsConstructor
public class RestaurantCategoryContorller {

    private final RestaurantCategoryService restaurantCategoryService;

    // 카테고리 등록
    @PostMapping
    public RestaurantCategoryResponseDto createCategories(
            @RequestBody RestaurantCategoriesRequestDto restaurantCategoriesRequestDto,
            @RequestHeader(value = "X-User-Id", required = true) String userId
    ){
        return restaurantCategoryService.createCategories(restaurantCategoriesRequestDto,userId);
    }

    // 카테고리 목록 조회
    @GetMapping
    public List<RestaurantCategory> getAllCategories(){
        return restaurantCategoryService.getAllCategories();
    }

    // 카테고리 수정
    @PutMapping("/{category_id}")
    public RestaurantCategoryResponseDto updateCategories(
            @PathVariable("category_id") UUID categoryId,
            @RequestBody RestaurantCategoriesRequestDto restaurantCategoriesRequestDto,
            @RequestHeader(value = "X-User-Id", required = true) String userId
    ){
        return restaurantCategoryService.updateCategories(categoryId,restaurantCategoriesRequestDto,userId);
    }

    // 카테고리 삭제
}
