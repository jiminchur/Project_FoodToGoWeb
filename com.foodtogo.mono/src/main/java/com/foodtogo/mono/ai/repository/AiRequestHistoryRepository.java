package com.foodtogo.mono.ai.repository;

import com.foodtogo.mono.ai.core.AiRequestHistory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface AiRequestHistoryRepository extends JpaRepository<AiRequestHistory,UUID> {
}
