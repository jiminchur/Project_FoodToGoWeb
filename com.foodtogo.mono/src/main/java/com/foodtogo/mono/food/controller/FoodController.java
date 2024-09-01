package com.foodtogo.mono.food.controller;

import com.foodtogo.mono.food.core.Food;
import com.foodtogo.mono.food.dto.FoodRequestDto;
import com.foodtogo.mono.food.dto.FoodResponseDto;
import com.foodtogo.mono.food.service.FoodService;
import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
@Slf4j
public class FoodController {

    private final FoodService foodService;

    // 가게에 속한 음식 등록
    @PostMapping("/restaurants/{restaurant_id}/foods")
    public FoodResponseDto createFoods(
            @PathVariable("restaurant_id") Restaurant restaurant,
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestHeader(value = "X-Role") String role,
            @RequestBody FoodRequestDto foodRequestDto
    ) {
        checkUserPermissions(role); // 권한 체크
        return foodService.createFoods(foodRequestDto, restaurant, userId, role);
    }

    // 가게에 속한 음식 조회
    @GetMapping("/restaurants/{restaurant_id}/foods")
    public Page<Food> getRestaurantFoods(
            @PathVariable("restaurant_id") Restaurant restaurant,
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestHeader(value = "X-Role") String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return foodService.getRestaurantFood(restaurant, userId, role, pageable);
    }

    // 음식 전체 조회 (운영진)
    @GetMapping("/foods")
    public Page<Food> getAllFoods(
            @RequestHeader(value = "X-Role") String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        checkAdminPermissions(role);
        Pageable pageable = PageRequest.of(page, size);
        return foodService.getAllFood(pageable);
    }

    // 음식 단건 조회
    @GetMapping("/foods/{food_id}")
    public FoodResponseDto getFoodsById(
            @PathVariable("food_id") UUID foodId,
            @RequestHeader(value = "X-Role") String role
    ) {
        checkAdminPermissions(role);
        return foodService.getFoodById(foodId);
    }

    // 음식 정보 수정
    @PutMapping("/foods/{food_id}")
    public FoodResponseDto updateFoods(
            @PathVariable("food_id") UUID foodId,
            @RequestBody FoodRequestDto foodRequestDto,
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestHeader(value = "X-Role") String role
    ) {
        checkUserPermissions(role); // 권한 체크
        return foodService.updateFood(foodId, foodRequestDto, userId, role);
    }

    // 음식 숨김 처리 및 복구
    @PatchMapping("/foods/{food_id}/sale")
    public void toggleIsHide(
            @PathVariable("food_id") UUID foodId,
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestHeader(value = "X-Role") String role
    ) {
        checkUserPermissions(role); // 권한 체크
        foodService.toggleIsSale(foodId, userId, role);
    }

    // 음식 삭제
    @DeleteMapping("/foods/{food_id}")
    public void deleteFood(
            @PathVariable("food_id") UUID foodId,
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestHeader(value = "X-Role") String role
    ) {
        checkUserPermissions(role); // 권한 체크
        foodService.deleteFood(foodId, userId, role);
    }

    // 음식 품절 처리 및 복구
    @PatchMapping("/foods/{food_id}/sold")
    public void toggleIsSale(
            @PathVariable("food_id") UUID foodId,
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestHeader(value = "X-Role") String role
    ) {
        checkUserPermissions(role); // 권한 체크
        foodService.toggleIsHidden(foodId, userId, role);
    }

    // 음식 검색
    @GetMapping("/foods/search")
    public ResponseEntity<Page<FoodResponseDto>> searchRestaurants(
            @RequestParam("query") String query,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam("sortBy") String sortBy,
            @RequestParam("isAsc") boolean isAsc
    ) {

        Page<FoodResponseDto> searchedFoods = foodService.searchFoods(query, page, size, sortBy, isAsc);
        log.info("음식 검색: {}, page: {}, size: {}", query, page, size);
        return new ResponseEntity<>(searchedFoods,HttpStatus.OK);
    }

    // 권한 체크 메서드
    private void checkUserPermissions(String role) {
        if ("USER".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "권한이 없습니다.");
        }
    }

    private void checkAdminPermissions(String role) {
        if (!"MANAGER".equals(role) && !"MASTER".equals(role)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. User role is not MANAGER.");
        }
    }
}
