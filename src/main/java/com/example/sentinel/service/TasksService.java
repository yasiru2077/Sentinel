package com.example.sentinel.service;

import com.example.sentinel.dto.request.TaskRequest;
import com.example.sentinel.dto.response.TaskResponse;
import com.example.sentinel.entity.Task;
import com.example.sentinel.entity.User;
import com.example.sentinel.exception.ResourceNotFoundException;
import com.example.sentinel.exception.UnauthorizedException;
import com.example.sentinel.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TasksService {

    private final TaskRepository taskRepository;

    @Transactional
    public TaskResponse create(TaskRequest request, User user) {

        if (user == null) throw new UnauthorizedException("Authentication required");

        Task task = Task.builder()
                .title(request.title())
                .description(request.description())
                .createdBy(user)
                .build();

        return toResponse(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getAll(User user) {

        if (user == null) throw new UnauthorizedException("Authentication required");

        return taskRepository.findByUser(user)
                .stream().map(this::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public TaskResponse getById(UUID id, User user) {

        if (user == null) throw new UnauthorizedException("Authentication required");

        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        return toResponse(task);
    }


    @Transactional
    public TaskResponse update(UUID id, TaskRequest request, User user) {
        if (user == null) throw new UnauthorizedException("Authentication required");
        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        task.setTitle(request.title());
        task.setDescription(request.description());

        return toResponse(taskRepository.save(task));
    }

    @Transactional
    public void delete(UUID id, User user) {
        if (user == null) throw new UnauthorizedException("Authentication required");

        Task task = taskRepository.findByIdAndUser(id, user)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        taskRepository.delete(task);
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getProject().getId(),
                task.getStage().getId(),
                task.getStage().getStageName(),
                task.getCreatedBy().getId(),
                task.getPriority(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }


}
