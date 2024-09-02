package com.foodtogo.mono.food.controller;

import com.foodtogo.mono.Result;
import com.foodtogo.mono.food.core.Food;
import com.foodtogo.mono.food.dto.FoodRequestDto;
import com.foodtogo.mono.food.dto.FoodResponseDto;
import com.foodtogo.mono.food.dto.FoodSearchDto;
import com.foodtogo.mono.food.service.FoodService;
import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
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
    public Result<FoodResponseDto> createFoods(
            @PathVariable("restaurant_id") Restaurant restaurant,
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestHeader(value = "X-Role") String role,
            @Valid @RequestBody FoodRequestDto foodRequestDto
    ) {
        checkUserPermissions(role); // 권한 체크
        return Result.of(foodService.createFoods(foodRequestDto, restaurant, userId, role));
    }

    // 가게에 속한 음식 조회
    @GetMapping("/restaurants/{restaurant_id}/foods")
    public Result<Page<Food>> getRestaurantFoods(
            @PathVariable("restaurant_id") Restaurant restaurant,
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestHeader(value = "X-Role") String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        return Result.of(foodService.getRestaurantFood(restaurant, userId, role, pageable));
    }

    // 음식 전체 조회 (운영진)
    @GetMapping("/foods")
    public Result<Page<Food>> getAllFoods(
            @RequestHeader(value = "X-Role") String role,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        checkAdminPermissions(role);
        Pageable pageable = PageRequest.of(page, size);
        return Result.of(foodService.getAllFood(pageable));
    }

    // 음식 단건 조회
    @GetMapping("/foods/{food_id}")
    public Result<FoodResponseDto> getFoodsById(
            @PathVariable("food_id") UUID foodId,
            @RequestHeader(value = "X-Role") String role
    ) {
        checkAdminPermissions(role);
        return Result.of(foodService.getFoodById(foodId));
    }

    // 음식 정보 수정
    @PutMapping("/foods/{food_id}")
    public Result<FoodResponseDto> updateFoods(
            @PathVariable("food_id") UUID foodId,
            @Valid @RequestBody FoodRequestDto foodRequestDto,
            @RequestHeader(value = "X-User-Id") String userId,
            @RequestHeader(value = "X-Role") String role
    ) {
        checkUserPermissions(role); // 권한 체크
        return Result.of(foodService.updateFood(foodId, foodRequestDto, userId, role));
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
    public ResponseEntity<Result<Page<FoodResponseDto>>> searchRestaurants(
            FoodSearchDto foodSearchDto
    ) {
        int size = foodSearchDto.getValidatedSize();
        Page<FoodResponseDto> searchedFoods = foodService.searchFoods(foodSearchDto.getKeyword(), foodSearchDto.getPage(), size, foodSearchDto.getSortBy());
        return new ResponseEntity<>(Result.of(searchedFoods), HttpStatus.OK);
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
