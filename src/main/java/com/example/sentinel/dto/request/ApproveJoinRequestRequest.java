package com.example.sentinel.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ApproveJoinRequestRequest(
        @NotBlank(message = "Job position is required")

        @Size(max = 100)

        String jobPosition
) {
}
