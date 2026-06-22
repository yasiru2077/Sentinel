package com.example.sentinel.dto.response;

import com.example.sentinel.entity.Task;

import java.time.Instant;
import java.util.UUID;

public record TaskResponse(
        UUID id,
        UUID userId,
        String title,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
    public static TaskResponse of(Task task){
         return new TaskResponse(
                 task.getId(),
                 task.getUser().getId(),
                 task.getTitle(),
                 task.getDescription(),
                 task.getCreatedAt(),
                 task.getUpdatedAt()
         );
    }
}
