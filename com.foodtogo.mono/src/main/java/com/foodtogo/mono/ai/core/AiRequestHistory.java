package com.foodtogo.mono.ai.core;

import com.foodtogo.mono.log.BaseEntity;
import com.foodtogo.mono.user.core.domain.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.UuidGenerator;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.UUID;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "p_ai_request_history")
@EntityListeners(value = {AuditingEntityListener.class})
public class AiRequestHistory extends BaseEntity {

    @Id
    @UuidGenerator
    private UUID aiRequestHistoryId;

    // 음식점 소유자 ID, FK
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false )
    private User user;

    // 긴 텍스트 필드 설정
    @Column(columnDefinition = "TEXT")
    private String aiRequest;

    // 긴 텍스트 필드 설정
    @Column(columnDefinition = "TEXT")
    private String aiResponse;

    public AiRequestHistory(
        User user,
        String aiRequest,
        String aiResponse
    ){
        this.aiRequest = aiRequest;
        this.aiResponse = aiResponse;
        this.user = user;
    }
}
