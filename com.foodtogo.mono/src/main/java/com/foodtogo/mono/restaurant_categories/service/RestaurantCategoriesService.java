package com.foodtogo.mono.restaurant_categories.service;

import com.foodtogo.mono.restaurant_categories.core.RestaurantCategories;
import com.foodtogo.mono.restaurant_categories.dto.RestaurantCategoriesRequestDto;
import com.foodtogo.mono.restaurant_categories.dto.RestaurantCategoriesResponseDto;
import com.foodtogo.mono.restaurant_categories.repository.RestaurantCategoriesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

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

    // 카테고리 전체 조회
    public List<RestaurantCategories> getAllCategories() {
        return restaurantCategoriesRepository.findAll();
    }

    // 카테고리 수정
    @Transactional
    public RestaurantCategoriesResponseDto updateCategories(
            UUID restaurantCategoriesId,
            RestaurantCategoriesRequestDto restaurantCategoriesRequestDto,
            String userId
    ){
        RestaurantCategories restaurantCategories = restaurantCategoriesRepository.findById(restaurantCategoriesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 카테고리는 없습니다."));
        restaurantCategories.updateCategories(
                restaurantCategoriesRequestDto.getCategoryTitle()
        );

        restaurantCategories.setUpdatedBy(userId);
        RestaurantCategories updatedRestaurantCategories = restaurantCategoriesRepository.save(restaurantCategories);

        return toResponseDto(updatedRestaurantCategories);
    }
}
