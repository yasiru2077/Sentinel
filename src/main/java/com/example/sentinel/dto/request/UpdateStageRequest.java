package com.example.sentinel.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdateStageRequest(
        @NotBlank(message = "Stage name is required")
        @Size(max = 100)
        String stageName
) {
}
