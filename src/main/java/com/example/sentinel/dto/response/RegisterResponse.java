package com.example.sentinel.dto.response;


import com.example.sentinel.entity.User;

import java.util.UUID;

public record RegisterResponse(

        String accessToken,
        String tokenType,
        UUID userId,
        String username,
        String email

) {
    public static RegisterResponse of(String token, User user) {
        return new RegisterResponse(
                token,
                "Bearer",
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}