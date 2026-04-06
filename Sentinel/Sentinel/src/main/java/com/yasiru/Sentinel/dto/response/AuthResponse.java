package com.yasiru.Sentinel.dto.response;

import com.yasiru.Sentinel.entity.Role;
import com.yasiru.Sentinel.entity.User;

public record AuthResponse(
        String accessToken,
        String tokenType,
        Long userId,
        String username,
        String email,
        Role role
) {
    public static AuthResponse of(String token, User user) {
        return new AuthResponse(
                token,
                "Bearer",
                user.getId(),
                user.getUsername(),
                user.getEmail(),
                user.getRole());
    }
}
