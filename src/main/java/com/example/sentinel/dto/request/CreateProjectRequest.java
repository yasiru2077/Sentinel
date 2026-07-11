package com.example.sentinel.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateProjectRequest(

        @NotBlank(message = "Project name is required")
        @Size(max = 255)
        String projectName,

        @Size(max = 2000)
        String description

) {
}
