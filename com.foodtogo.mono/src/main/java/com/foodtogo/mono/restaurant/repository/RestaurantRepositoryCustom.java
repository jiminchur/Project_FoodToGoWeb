package com.foodtogo.mono.restaurant.repository;

import com.foodtogo.mono.restaurant.core.domain.Restaurant;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public class RestaurantRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Page<Restaurant> findRestaurants(Pageable pageable) {
        String sql = "SELECT * FROM p_restaurants WHERE deleted_at IS NULL";

        // 전체 레코드 수 조회
        Query countQuery = entityManager.createNativeQuery("SELECT COUNT(*) FROM p_restaurants WHERE deleted_at IS NULL");
        long totalCount = ((Number) countQuery.getSingleResult()).longValue();

        // 페이징 처리된 쿼리 실행
        Query query = entityManager.createNativeQuery(sql, Restaurant.class);
        query.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        query.setMaxResults(pageable.getPageSize());

        List<Restaurant> restaurants = query.getResultList();
        return new PageImpl<>(restaurants, pageable, totalCount);
    }

}
