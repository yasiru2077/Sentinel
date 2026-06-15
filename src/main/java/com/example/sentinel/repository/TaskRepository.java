package com.example.sentinel.repository;

import com.example.sentinel.entity.Task;
import com.example.sentinel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface TaskRepository extends JpaRepository<Task,UUID> {

    Optional<Task> findByIdAndUser(UUID id, User user);

    List<Task> findByUser(User user);
}
