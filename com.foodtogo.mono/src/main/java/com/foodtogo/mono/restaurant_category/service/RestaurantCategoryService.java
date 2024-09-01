package com.foodtogo.mono.restaurant_category.service;

import com.foodtogo.mono.restaurant_category.core.RestaurantCategory;
import com.foodtogo.mono.restaurant_category.dto.RestaurantCategoryRequestDto;
import com.foodtogo.mono.restaurant_category.dto.RestaurantCategoryResponseDto;
import com.foodtogo.mono.restaurant_category.repository.RestaurantCategoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class RestaurantCategoryService {

    private final RestaurantCategoryRepository restaurantCategoryRepository;

    // 카테고리 등록
    @Transactional
    public RestaurantCategoryResponseDto createCategories(
            RestaurantCategoryRequestDto restaurantCategoryRequestDto
    ){
        log.info("카테고리 등록 요청: {}", restaurantCategoryRequestDto);
        RestaurantCategory restaurantCategory = new RestaurantCategory(
                restaurantCategoryRequestDto.getCategoryTitle());
        restaurantCategoryRepository.save(restaurantCategory);

        log.info("카테고리 등록 완료: {}", restaurantCategory);
        return new RestaurantCategoryResponseDto(restaurantCategory);
    }

    // 카테고리 전체 조회
    @Transactional(readOnly = true)
    public List<RestaurantCategory> getAllCategories() {
        log.info("전체 카테고리 조회 요청");
        List<RestaurantCategory> categories = restaurantCategoryRepository.findAllAndDeletedAtIsNull();
        log.info("전체 카테고리 조회 완료: {}", categories);
        return categories;
    }

    // 카테고리 수정
    @Transactional
    public RestaurantCategoryResponseDto updateCategories(
            UUID categoryId,
            RestaurantCategoryRequestDto restaurantCategoryRequestDto
    ){
        log.info("카테고리 수정 요청: ID={}, 데이터={}", categoryId, restaurantCategoryRequestDto);
        RestaurantCategory restaurantCategory = restaurantCategoryRepository.findById(categoryId)
                .filter(o -> o.getDeletedAt() == null)
                .orElseThrow(() -> {
                    log.warn("카테고리 수정 실패: 해당 카테고리 없음, ID={}", categoryId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 카테고리는 없습니다.");
                });

        restaurantCategory.updateCategories(
                restaurantCategoryRequestDto.getCategoryTitle());
        restaurantCategoryRepository.save(restaurantCategory);

        log.info("카테고리 수정 완료: {}", restaurantCategory);
        return new RestaurantCategoryResponseDto(restaurantCategory);
    }

    // 카테고리 삭제
    @Transactional
    public void deleteCategory(
            UUID categoryId,
            String userId
    ) {
        log.info("카테고리 삭제 요청: ID={}", categoryId);
        RestaurantCategory restaurantCategory = restaurantCategoryRepository.findById(categoryId)
                .filter(o -> o.getDeletedAt() == null)
                .orElseThrow(() -> {
                    log.warn("카테고리 삭제 실패: 해당 카테고리 없음, ID={}", categoryId);
                    return new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 카테고리는 없습니다.");
                });

        restaurantCategory.deleteCategory(userId);
        restaurantCategoryRepository.save(restaurantCategory);
        log.info("카테고리 삭제 완료: ID={}", categoryId);
    }

}
