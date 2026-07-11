package com.example.sentinel.dto.response;

import com.example.sentinel.entity.Stage;

import java.time.Instant;
import java.util.UUID;

public record StageResponse(
        UUID id,
        UUID projectId,
        String stageName,
        Integer orderIndex,
        UUID createdById,
        Instant createdAt
) {
    public static StageResponse of(Stage stage) {
        return new StageResponse(
                stage.getId(),
                stage.getProject().getId(),
                stage.getStageName(),
                stage.getOrderIndex(),
                stage.getCreatedBy().getId(),
                stage.getCreatedAt()
        );
    }
}