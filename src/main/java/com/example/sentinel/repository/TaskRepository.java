package com.example.sentinel.repository;

import com.example.sentinel.entity.Project;
import com.example.sentinel.entity.Stage;
import com.example.sentinel.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TaskRepository extends JpaRepository<Task, UUID> {

    boolean existsByStage(Stage stage);

    Optional<Task> findByIdAndProject(UUID id, Project project);

    List<Task> findByProject(Project project);

}
