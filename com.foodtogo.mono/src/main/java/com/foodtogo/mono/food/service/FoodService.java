package com.foodtogo.mono.food.service;

import com.foodtogo.mono.food.core.Food;
import com.foodtogo.mono.food.dto.FoodRequestDto;
import com.foodtogo.mono.food.dto.FoodResponseDto;
import com.foodtogo.mono.food.repository.FoodRepository;
import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FoodService {

    private final FoodRepository foodRepository;

    // 가게에 속한 음식 등록
    @Transactional
    public FoodResponseDto createFoods(
            FoodRequestDto foodRequestDto,
            Restaurant restaurant,
            String userId
    ){
        Food food = Food.createFoods(foodRequestDto,restaurant,userId);
        Food savedFood = foodRepository.save(food);

        return toResponseDto(savedFood);
    }

    private FoodResponseDto toResponseDto(
            Food food
    ) {
        return new FoodResponseDto(
                food.getFoodInfoId()
                , food.getFoodInfoTitle()
                , food.getFoodInfoDesc()
                , food.getFoodInfoPrice()
                , food.getIsHidden()
                , food.getIsSale()
        );
    }
}
