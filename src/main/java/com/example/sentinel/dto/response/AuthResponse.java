package com.example.sentinel.dto.response;

import com.example.sentinel.entity.User;

public record AuthResponse(String accessToken,
                           String tokenType,
                           Long userId,
                           String username,
                           String email) {

    public static AuthResponse of(String token, User user){
        return new AuthResponse(token,
                "Bearer",
                user.getId(),
                user.getUsername(),
                user.getEmail()
                );
    }

}
