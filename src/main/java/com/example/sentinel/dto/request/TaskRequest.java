package com.example.sentinel.dto.request;

import com.example.sentinel.entity.Priority;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record TaskRequest(

        @NotBlank(message = "Title is required")
        @Size(max = 500)
        String title,

        @Size(max = 2000)
        String description,

        @NotNull(message = "Stage is required")
        UUID stageId,

        @NotNull(message = "Priority is required")
        Priority priority

) {



}
