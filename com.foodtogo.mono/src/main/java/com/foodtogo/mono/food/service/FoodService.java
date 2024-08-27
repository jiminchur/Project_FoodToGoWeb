package com.foodtogo.mono.food.service;

import com.foodtogo.mono.food.core.Food;
import com.foodtogo.mono.food.dto.FoodRequestDto;
import com.foodtogo.mono.food.dto.FoodResponseDto;
import com.foodtogo.mono.food.repository.FoodRepository;
import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

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

    // 가게에 속한 음식 조회 (고객 / 운영진,가게주인)
    public Page<Food> getRestaurantFood(
            Restaurant restaurant
            , String role
            , Pageable pageable
    ){
        if(role.equals("User")){
            return foodRepository.findByRestaurantAndIsHiddenFalse(restaurant,pageable);
        } else {
            return foodRepository.findByRestaurant(restaurant,pageable);
        }
    }

    // 음식 전체 조회 (운영진)
    public Page<Food> getAllFood(
            Pageable pageable
    ){
        return foodRepository.findAll(pageable);
    }

    // 음식 단건 조회 (운영진)
    public FoodResponseDto getFoodById(
            UUID foodId
    ){
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당음식은 없습니다."));
        return toResponseDto(food);
    }

    // 음식 상세 정보 수정
    @Transactional
    public FoodResponseDto updateFood(
            UUID foodId
            , FoodRequestDto foodRequestDto
            , String userId
    ){
        Food food = foodRepository.findById(foodId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"해당 음식은 없습니다."));
        food.updateFood(
                foodRequestDto.getFoodInfoTitle()
                ,foodRequestDto.getFoodInfoDesc()
                ,foodRequestDto.getFoodInfoPrice()
        );

        food.setUpdatedBy(userId);

        Food updatedFood = foodRepository.save(food);

        return toResponseDto(updatedFood);
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
