package com.example.sentinel.dto.response;

import com.example.sentinel.entity.User;

import java.util.UUID;

public record LoginResponse(

        String accessToken,
        String tokenType,
        UUID userId,
        String username,
        String email

) {
    public static LoginResponse of
            (
                    String token, User user
            ) {
        return new LoginResponse(
                token,
                "Bearer",
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}