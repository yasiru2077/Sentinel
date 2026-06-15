package com.example.sentinel.dto.response;

import java.time.Instant;
import java.util.UUID;

public record TaskResponse(
        UUID id,
        String title,
        String description,
        Instant createdAt,
        Instant updatedAt
) {
}
