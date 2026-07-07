package com.example.sentinel.controller;

import com.example.sentinel.dto.request.TaskRequest;
import com.example.sentinel.dto.response.ApiResponse;
import com.example.sentinel.dto.response.TaskResponse;
import com.example.sentinel.entity.User;
import com.example.sentinel.service.TasksService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TasksService tasksService;

    @PostMapping
    public ResponseEntity<ApiResponse<TaskResponse>> create(
            @PathVariable UUID projectId,
            @Valid @RequestBody TaskRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(tasksService.create(projectId, request, user), "Task created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TaskResponse>>> getAll(
            @PathVariable UUID projectId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(tasksService.getAll(projectId, user)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> getById(
            @PathVariable UUID projectId,
            @PathVariable UUID id,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(tasksService.getById(projectId, id, user)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskResponse>> update(
            @PathVariable UUID projectId,
            @PathVariable UUID id,
            @Valid @RequestBody TaskRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(tasksService.update(projectId, id, request, user), "Task updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable UUID projectId,
            @PathVariable UUID id,
            @AuthenticationPrincipal User user) {
        tasksService.delete(projectId, id, user);
        return ResponseEntity.ok(ApiResponse.success(null, "Task deleted successfully"));
    }
}