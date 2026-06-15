package com.example.sentinel.service;

import com.example.sentinel.dto.request.TaskRequest;
import com.example.sentinel.dto.response.TaskResponse;
import com.example.sentinel.entity.Task;
import com.example.sentinel.entity.User;
import com.example.sentinel.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public TaskResponse create(TaskRequest request, User user) {
        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .user(user)
                .build();
        return toResponse(taskRepository.save(task));
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(), task.getTitle(),
                task.getDescription(), task.getCreatedAt(), task.getUpdatedAt()
        );
    }

}
