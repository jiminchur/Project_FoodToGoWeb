package com.foodtogo.mono.restaurant_categories.service;

import com.foodtogo.mono.restaurant_categories.core.RestaurantCategories;
import com.foodtogo.mono.restaurant_categories.dto.RestaurantCategoriesRequestDto;
import com.foodtogo.mono.restaurant_categories.dto.RestaurantCategoriesResponseDto;
import com.foodtogo.mono.restaurant_categories.repository.RestaurantCategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class RestaurantCategoriesService {

    private final RestaurantCategoriesRepository restaurantCategoriesRepository;

    // 카테고리 등록
    @Transactional
    public RestaurantCategoriesResponseDto createCategories(
            RestaurantCategoriesRequestDto restaurantCategoriesRequestDto,
            String userId
    ){
        RestaurantCategories restaurantCategories = RestaurantCategories.createCategories(restaurantCategoriesRequestDto,userId);
        RestaurantCategories savedRestaurantCategories = restaurantCategoriesRepository.save(restaurantCategories);

        return toResponseDto(savedRestaurantCategories);
    }

    private RestaurantCategoriesResponseDto toResponseDto(
            RestaurantCategories restaurantCategories
    ){
        return new RestaurantCategoriesResponseDto(
                restaurantCategories.getCategoryId()
                ,restaurantCategories.getCategoryTitle()
        );
    }
}
