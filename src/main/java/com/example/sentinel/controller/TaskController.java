package com.example.sentinel.controller;

import com.example.sentinel.dto.request.TaskRequest;
import com.example.sentinel.dto.response.TaskResponse;
import com.example.sentinel.entity.User;
import com.example.sentinel.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping
    public ResponseEntity<TaskResponse> create(@Valid @RequestBody TaskRequest request,
                                               @AuthenticationPrincipal User user) {

        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.create(request, user));
    }

    @GetMapping
    public ResponseEntity<List<TaskResponse>> getAll(@AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.getAll(user));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponse> getById(@PathVariable UUID id, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.getById(id, user));
    }


    @PutMapping("/{id}")
    public ResponseEntity<TaskResponse> update(@PathVariable UUID id, @Valid @RequestBody TaskRequest request, @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(taskService.update(id, request, user));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id,
                                       @AuthenticationPrincipal User user){
        taskService.delete(id,user);
        return ResponseEntity.noContent().build();
    }

}
