package com.yasiru.Sentinel.service;

import com.yasiru.Sentinel.config.JwtService;
import com.yasiru.Sentinel.config.TokenBlacklistService;
import com.yasiru.Sentinel.dto.request.LoginRequest;
import com.yasiru.Sentinel.dto.request.RegisterRequest;
import com.yasiru.Sentinel.dto.response.AuthResponse;
import com.yasiru.Sentinel.dto.response.LogoutResponse;
import com.yasiru.Sentinel.entity.User;
import com.yasiru.Sentinel.entity.UserStatus;
import com.yasiru.Sentinel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final TokenBlacklistService tokenBlacklistService;

    @Transactional
    public AuthResponse register(RegisterRequest request){
        if (userRepository.existsByEmail(request.email())){
            throw new IllegalArgumentException("Email already is use");
        }

        if (userRepository.existsByUsername(request.username())){
            throw new IllegalArgumentException("Username already taken");
        }

        User user = User.builder()
                .fullName(request.fullName())
                .username(request.username())
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .role(request.role())
                .status(UserStatus.ACTIVE)
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return  AuthResponse.of(token,user);
    }

    @Transactional
    public AuthResponse login(LoginRequest request){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    request.email(),
                    request.password()
                )
        );

        User user = userRepository.findByEmail(request.email())
                .orElseThrow(()->new IllegalArgumentException("User not found"));

        String token = jwtService.generateToken(user);

        return AuthResponse.of(token,user);

    }

    public LogoutResponse logout(String authHeader){
        if (authHeader == null || !authHeader.startsWith("Bearer ")){
            throw new IllegalArgumentException("Missing or invalid Authorization header");
        }

        String token = authHeader.substring(7);
        tokenBlacklistService.blacklist(token);
        return new LogoutResponse("Logged out successfully");
    }



}
