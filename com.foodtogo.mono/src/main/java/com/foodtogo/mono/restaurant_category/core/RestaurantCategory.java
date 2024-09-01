package com.foodtogo.mono.restaurant_category.core;

import com.foodtogo.mono.log.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "p_restaurant_categories")
@EntityListeners(value = {AuditingEntityListener.class})
public class RestaurantCategory extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID categoryId;

    @Column(length = 50, unique = true)
    private String categoryTitle;

    // 카테고리 생성
    public RestaurantCategory(
            String categoryTitle
    ){
        this.categoryTitle = categoryTitle;
    }

    // 카테고리 수정
    public void updateCategories(
            String categoryTitle
    ){
        this.categoryTitle = categoryTitle;
    }

    // 카테고리 삭제
    public void deleteCategory(
            String deleteBy
    ) {
        this.deletedBy = deleteBy;
        this.deletedAt = LocalDateTime.now();
    }
}
