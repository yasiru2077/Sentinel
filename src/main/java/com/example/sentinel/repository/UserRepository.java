package com.example.sentinel.repository;

import com.example.sentinel.entity.User;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User,Long> {

    boolean existByEmail(@NotBlank(message = "Email is required") @Email(message = "Invalid email format") String email);

    boolean existsByUsername(@NotBlank(message = "Username is required") @Size(min = 3,max = 50,message = "Username must be between 3 and 50 characters") String username);
}
