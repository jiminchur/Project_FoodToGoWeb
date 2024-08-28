package com.foodtogo.mono.restaurant_category.service;

import com.foodtogo.mono.restaurant_category.core.RestaurantCategory;
import com.foodtogo.mono.restaurant_category.dto.RestaurantCategoriesRequestDto;
import com.foodtogo.mono.restaurant_category.dto.RestaurantCategoryResponseDto;
import com.foodtogo.mono.restaurant_category.repository.RestaurantCategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RestaurantCategoryService {

    private final RestaurantCategoryRepository restaurantCategoryRepository;

    // 카테고리 등록
    @Transactional
    public RestaurantCategoryResponseDto createCategories(
            RestaurantCategoriesRequestDto restaurantCategoriesRequestDto,
            String userId
    ){
        RestaurantCategory restaurantCategory = RestaurantCategory.createCategories(restaurantCategoriesRequestDto,userId);
        RestaurantCategory savedRestaurantCategory = restaurantCategoryRepository.save(restaurantCategory);

        return toResponseDto(savedRestaurantCategory);
    }

    private RestaurantCategoryResponseDto toResponseDto(
            RestaurantCategory restaurantCategory
    ){
        return new RestaurantCategoryResponseDto(
                restaurantCategory.getCategoryId()
                , restaurantCategory.getCategoryTitle()
        );
    }

    // 카테고리 전체 조회
    public List<RestaurantCategory> getAllCategories() {
        return restaurantCategoryRepository.findAll();
    }

    // 카테고리 수정
    @Transactional
    public RestaurantCategoryResponseDto updateCategories(
            UUID restaurantCategoriesId,
            RestaurantCategoriesRequestDto restaurantCategoriesRequestDto,
            String userId
    ){
        RestaurantCategory restaurantCategory = restaurantCategoryRepository.findById(restaurantCategoriesId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 카테고리는 없습니다."));
        restaurantCategory.updateCategories(
                restaurantCategoriesRequestDto.getCategoryTitle()
        );

        restaurantCategory.setUpdatedBy(userId);
        RestaurantCategory updatedRestaurantCategory = restaurantCategoryRepository.save(restaurantCategory);

        return toResponseDto(updatedRestaurantCategory);
    }
}
