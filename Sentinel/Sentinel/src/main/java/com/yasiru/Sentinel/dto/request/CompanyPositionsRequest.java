package com.yasiru.Sentinel.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompanyPositionsRequest(
        @NotBlank(message = "Title is required")
        String title,
        @NotNull(message = "Hourly rate is required")
        Double hourly_rate


        ) {
}
