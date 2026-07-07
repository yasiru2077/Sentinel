package com.example.sentinel.dto.request;

import jakarta.validation.constraints.NotBlank;

public record CreateJoinRequestRequest(
        @NotBlank(message = "Company name is required")

        String companyName
) {
}
