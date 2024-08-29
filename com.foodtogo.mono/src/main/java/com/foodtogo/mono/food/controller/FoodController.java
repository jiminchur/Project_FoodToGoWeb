package com.foodtogo.mono.food.controller;

import com.foodtogo.mono.food.core.Food;
import com.foodtogo.mono.food.dto.FoodRequestDto;
import com.foodtogo.mono.food.dto.FoodResponseDto;
import com.foodtogo.mono.food.service.FoodService;
import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    // 가게에 속한 음식 등록
    @PostMapping("/restaurants/{restaurant_id}/foods")
    public FoodResponseDto createFoods(
            @PathVariable("restaurant_id") Restaurant restaurant,
            @RequestHeader(value = "X-User-Id", required = true) String userId,
            @RequestBody FoodRequestDto foodRequestDto
    ){
        return foodService.createFoods(foodRequestDto,restaurant,userId);
    }

    // 가게에 속한 음식 조회 (고객 / 운영진,가게주인)
    @GetMapping("/restaurants/{restaurant_id}/foods")
    public Page<Food> getRestaurantFoods(
            @PathVariable("restaurant_id") Restaurant restaurant
            , @RequestHeader("X-Role") String role
            , @RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "10") int size
    ){
        Pageable pageable = PageRequest.of(page, size);
        return foodService.getRestaurantFood(restaurant,role,pageable);
    }

    // 음식 전체 조회 (운영진)
    @GetMapping("/foods")
    public Page<Food> getAllFoods(
            @RequestHeader("X-Role") String role
            , @RequestParam(defaultValue = "0") int page
            , @RequestParam(defaultValue = "10") int size
    ){
        if(!"Manager".equals(role) && !"Master".equals(role)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. User role is not MANAGER.");
        }
        Pageable pageable = PageRequest.of(page, size);
        return foodService.getAllFood(pageable);
    }

    // 음식 단건 조회 (운영진)
    @GetMapping("/foods/{food_id}")
    public FoodResponseDto getFoodsById(
            @PathVariable("food_id") UUID foodId
            , @RequestHeader("X-Role") String role
    ){
        if(!"Manager".equals(role) && !"Master".equals(role)){
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Access denied. User role is not MANAGER.");
        }
        return foodService.getFoodById(foodId);
    }

    // 가게 상세 정보 수정
    @PutMapping("/foods/{food_id}")
    public FoodResponseDto updateFoods(
            @PathVariable("food_id") UUID foodId
            , @RequestBody FoodRequestDto foodRequestDto
            , @RequestHeader(value = "X-User-Id", required = true) String userId
    ){
        return foodService.updateFood(foodId,foodRequestDto,userId);
    }

    // 음식 숨김처리 및 복구
    @PatchMapping("/foods/{food_id}/sale")
    public void toggleIsHide(
            @PathVariable("food_id") UUID foodId
            , @RequestHeader(value = "X-User-Id", required = true) String userId
    ){
        foodService.toggleIsSale(foodId,userId);
    }

    // 음식 삭제
    @DeleteMapping("/foods/{food_id}")
    public void deleteFood(
            @PathVariable("food_id") UUID foodId
            , @RequestHeader(value = "X-User-Id", required = true) String userId
    ){
        foodService.deleteFood(foodId,userId);
    }

    // 음식 품절처리 및 복구
    @PatchMapping("/foods/{food_id}/sold")
    public void toggleIsSale(
            @PathVariable("food_id") UUID foodId
            , @RequestHeader(value = "X-User-Id", required = true) String userId
    ){
        foodService.toggleIsHidden(foodId,userId);
    }
}