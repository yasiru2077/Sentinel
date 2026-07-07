package com.example.sentinel.dto.response;

import com.example.sentinel.entity.Project;

import java.time.Instant;
import java.util.UUID;

public record ProjectResponse(
        UUID id,
        UUID companyId,
        String projectName,
        String description,
        UUID createdById,
        Instant createdAt,
        Instant updatedAt
) {

    public static ProjectResponse of(Project project){
        return new ProjectResponse(
                project.getId(),
                project.getCompany().getId(),
                project.getProjectName(),
                project.getDescription(),
                project.getCreatedBy().getId(),
                project.getCreatedAt(),
                project.getUpdatedAt()
        );
    }

}
