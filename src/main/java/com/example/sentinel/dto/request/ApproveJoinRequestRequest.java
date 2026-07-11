package com.example.sentinel.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record ApproveJoinRequestRequest(
        com.example.sentinel.entity.RoleType roleType
) {
}
