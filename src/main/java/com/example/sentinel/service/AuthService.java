package com.example.sentinel.service;

import com.example.sentinel.dto.request.RegisterRequest;
import com.example.sentinel.dto.response.AuthResponse;
import com.example.sentinel.entity.User;
import com.example.sentinel.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
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
    public AuthResponse register(RegisterRequest request){
        if (userRepository.existByEmail(request.email())){
            throw new IllegalArgumentException("Email already in use");
        }

        if (userRepository.existsByUsername(request.username())){
            throw new IllegalArgumentException("Username already taken");
        }

        User user = User.builder()
                .username(request.username())
                .email(request.email())
                .passwordHash(passwordEncoder.encode(request.password()))
                .build();

        userRepository.save(user);

        String token = jwtService.generateToken(user);
        return AuthResponse.of(token,user);

    }

}
