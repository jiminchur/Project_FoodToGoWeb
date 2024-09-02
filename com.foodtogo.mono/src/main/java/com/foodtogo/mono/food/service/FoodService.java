package com.foodtogo.mono.food.service;

import com.foodtogo.mono.food.core.Food;
import com.foodtogo.mono.food.dto.FoodRequestDto;
import com.foodtogo.mono.food.dto.FoodResponseDto;
import com.foodtogo.mono.food.repository.FoodRepository;
import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FoodService {

    private final FoodRepository foodRepository;

    // 음식 생성
    @Transactional
    public FoodResponseDto createFoods(
            FoodRequestDto foodRequestDto,
            Restaurant restaurant,
            String userId,
            String role
    ) {
        // 가게 주인 검증
        validateOwner(restaurant, userId, role);

        // 음식 객체 생성 및 저장
        Food food = new Food(
                foodRequestDto.getFoodInfoTitle(),
                foodRequestDto.getFoodInfoDesc(),
                foodRequestDto.getFoodInfoPrice(),
                restaurant);
        foodRepository.save(food);

        log.info("음식 등록 완료: {}", food);
        return new FoodResponseDto(food);
    }

    // 가게에 대한 음식 조회 (숨김처리 여부)
    @Transactional(readOnly = true)
    public Page<Food> getRestaurantFood(
            Restaurant restaurant,
            String userId,
            String role,
            Pageable pageable
    ) {
        // 사용자 권한에 따라 음식 조회
        if (!"MASTER".equals(role) && !"MANAGER".equals(role) && "USER".equals(role) &&
                restaurant.getUser().getUserId().equals(UUID.fromString(userId))) {
            return foodRepository.findByRestaurantAndIsHiddenFalseAndDeletedAtIsNull(restaurant, pageable);
        }
        return foodRepository.findByRestaurant(restaurant, pageable);
    }

    // 운영진을 위한 음식 전체 조회
    @Transactional(readOnly = true)
    public Page<Food> getAllFood(Pageable pageable) {
        return foodRepository.findAll(pageable);
    }

    // 운영진을 위한 음식 단건 조회
    @Transactional(readOnly = true)
    public FoodResponseDto getFoodById(UUID foodId) {
        Food food = findFoodId(foodId);
        return new FoodResponseDto(food);
    }

    // 음식 수정
    @Transactional
    public FoodResponseDto updateFood(
            UUID foodId,
            FoodRequestDto foodRequestDto,
            String userId,
            String role
    ) {
        Food food = findFoodId(foodId);
        validateOwner(food.getRestaurant(), userId, role); // 가게 주인 검증
        food.updateFood(
                foodRequestDto.getFoodInfoTitle(),
                foodRequestDto.getFoodInfoDesc(),
                foodRequestDto.getFoodInfoPrice()); // 음식 정보 업데이트
        foodRepository.save(food);

        log.info("음식 수정 완료: {}", food);
        return new FoodResponseDto(food);
    }

    // 음식 판매 상태 변경
    @Transactional
    public void toggleIsSale(UUID foodId, String userId, String role) {
        Food food = findFoodId(foodId);
        validateOwner(food.getRestaurant(), userId, role); // 가게 주인 검증
        food.setIsSale(!food.getIsSale()); // 판매 상태 토글
        foodRepository.save(food);

        log.info("음식 판매 상태 변경 완료: {}", food);
    }

    // 음식 삭제
    @Transactional
    public void deleteFood(UUID foodId, String userId, String role) {
        Food food = findFoodId(foodId);
        validateOwner(food.getRestaurant(), userId, role); // 가게 주인 검증
        food.deleteFood(userId); // 음식 삭제 처리
        foodRepository.save(food);

        log.info("음식 삭제 완료: {}", food);
    }

    // 음식 숨김 처리
    @Transactional
    public void toggleIsHidden(UUID foodId, String userId, String role) {
        Food food = findFoodId(foodId);
        validateOwner(food.getRestaurant(), userId, role); // 가게 주인 검증

        food.setIsHidden(!food.getIsHidden()); // 숨김 상태 토글
        foodRepository.save(food);

        log.info("음식 숨김 상태 변경 완료: {}", food);
    }

    // 음식 검색
    public Page<FoodResponseDto> searchFoods(
            String query,
            int page,
            int size,
            String sortBy,
            boolean isAsc
    ) {
        Pageable pageable = convertToPage(
                page,
                size,
                sortBy,
                isAsc
        );

        Page<Food> foodList = foodRepository.findByFoodInfoTitleContainingAndDeletedAtIsNull(query,pageable);

        return foodList.map(FoodResponseDto::new);
    }

    // 가게 주인 검증 메서드
    private void validateOwner(Restaurant restaurant, String userId, String role) {
        log.info("가게 주인 검증 시작");
        if (!"MASTER".equals(role) && !"MANAGER".equals(role)) {
            if (!restaurant.getUser().getUserId().equals(UUID.fromString(userId))) {
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "해당 아이디는 가게 주인이 아닙니다.");
            }
        }
        log.info("가게 주인 검증 완료");
    }

    // 음식 아이디로 데이터 찾기
    private Food findFoodId(UUID foodId) {
        return foodRepository.findById(foodId)
                .filter(o -> o.getDeletedAt() == null)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "해당 음식은 없습니다."));
    }

    // 페이지 처리
    private Pageable convertToPage(int page, int size, String sortBy, boolean isAsc) {

        Sort.Direction direction = isAsc ? Sort.Direction.ASC : Sort.Direction.DESC;
        Sort sort = Sort.by(direction, sortBy);

        return PageRequest.of(page, size, sort);
    }
}
