package com.example.sentinel.dto.response;

import com.example.sentinel.entity.Role;
import com.example.sentinel.entity.RoleType;

import java.util.UUID;

public record RoleResponse(
        UUID id,
        UUID userId,
        String username,
        UUID companyId,
        String jobPosition,
        RoleType roleType
) {

    public static RoleResponse of(Role role) {
        return new RoleResponse(
                role.getId(),
                role.getUser().getId(),
                role.getUser().getUsername(),
                role.getCompany().getId(),
                role.getJobPosition(),
                role.getRoleType()
        );
    }

}
