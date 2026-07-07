package com.example.sentinel.dto.response;

import com.example.sentinel.entity.ProjectMember;

import java.time.Instant;
import java.util.UUID;

public record ProjectMemberResponse(
        UUID id,
        UUID projectId,
        UUID userId,
        String username,
        UUID addedById,
        Instant createdAt
) {
    public static ProjectMemberResponse of(ProjectMember member) {
        return new ProjectMemberResponse(
                member.getId(),
                member.getProject().getId(),
                member.getUser().getId(),
                member.getUser().getUsername(),
                member.getAddedBy().getId(),
                member.getCreatedAt()
        );
    }
}
