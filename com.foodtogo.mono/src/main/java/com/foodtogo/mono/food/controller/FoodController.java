package com.foodtogo.mono.food.controller;

import com.foodtogo.mono.food.dto.FoodRequestDto;
import com.foodtogo.mono.food.dto.FoodResponseDto;
import com.foodtogo.mono.food.service.FoodService;
import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/")
@RequiredArgsConstructor
public class FoodController {

    private final FoodService foodService;

    @PostMapping("/restaurants/{restaurant_id}/foods")
    public FoodResponseDto createFoods(
            @PathVariable("restaurant_id") Restaurant restaurant,
            @RequestHeader(value = "X-User-Id", required = true) String userId,
            @RequestBody FoodRequestDto foodRequestDto
    ){
        return foodService.createFoods(foodRequestDto,restaurant,userId);
    }
}
