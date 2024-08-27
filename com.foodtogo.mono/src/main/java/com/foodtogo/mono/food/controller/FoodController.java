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
import org.springframework.web.bind.annotation.*;

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
}
