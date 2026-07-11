package com.example.sentinel.dto.response;

import com.example.sentinel.entity.Priority;
import com.example.sentinel.entity.Task;

import java.time.Instant;
import java.util.UUID;

public record TaskResponse(

        UUID id,
        UUID projectId,
        UUID stageId,
        String stageName,
        UUID createdById,
        String createdByUsername,
        Priority priority,
        String title,
        String description,
        Instant createdAt,
        Instant updatedAt

) {

    public static TaskResponse of(Task task){
        return new TaskResponse(
                task.getId(),
                task.getProject().getId(),
                task.getStage().getId(),
                task.getStage().getStageName(),
                task.getCreatedBy().getId(),
                task.getCreatedBy().getUsername(),
                task.getPriority(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

}
