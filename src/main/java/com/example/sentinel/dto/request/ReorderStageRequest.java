package com.example.sentinel.dto.request;

import jakarta.validation.constraints.NotNull;

public record ReorderStageRequest(
        @NotNull(message = "Order index is required")
        Integer orderIndex
) {
}
