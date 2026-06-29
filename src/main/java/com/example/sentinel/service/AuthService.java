package com.example.sentinel.service;

import com.example.sentinel.dto.request.LoginRequest;
import com.example.sentinel.dto.request.RegisterRequest;
import com.example.sentinel.dto.response.LoginResponse;
import com.example.sentinel.dto.response.RegisterResponse;
import com.example.sentinel.entity.User;
import com.example.sentinel.exception.ConflictException;
import com.example.sentinel.exception.ResourceNotFoundException;
import com.example.sentinel.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;


    @Transactional
    public RegisterResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.email())) {
            throw new ConflictException("Email already in use");
        }

        if (userRepository.existsByUsername(request.username())) {
            throw new ConflictException("Username already taken");
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return RegisterResponse.of(token, user);

    }

    public LoginResponse login(LoginRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.email(),
                        request.password()
                )
        );

        User user = userRepository.findByEmail(request.email()).
                orElseThrow(
                        () -> new ResourceNotFoundException("User not found")
                );

        String token = jwtService.generateToken(user);
        return LoginResponse.of(token, user);
    }

}
