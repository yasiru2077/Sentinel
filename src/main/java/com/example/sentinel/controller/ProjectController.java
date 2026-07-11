package com.example.sentinel.controller;

import com.example.sentinel.dto.request.CreateProjectRequest;
import com.example.sentinel.dto.response.ApiResponse;
import com.example.sentinel.dto.response.ProjectResponse;
import com.example.sentinel.entity.User;
import com.example.sentinel.service.ProjectService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
public class ProjectController {

    private final ProjectService projectService;

    @PostMapping("/api/companies/{companyId}/projects")
    public ResponseEntity<ApiResponse<ProjectResponse>> create(
            @PathVariable UUID companyId,
            @Valid @RequestBody CreateProjectRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(projectService.create(companyId, request, user), "Project created successfully"));
    }

    @GetMapping("/api/companies/{companyId}/projects")
    public ResponseEntity<ApiResponse<List<ProjectResponse>>> getByCompany(
            @PathVariable UUID companyId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(projectService.getByCompany(companyId, user)));
    }

    @GetMapping("/api/projects/{id}")
    public ResponseEntity<ApiResponse<ProjectResponse>> getById(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(projectService.getById(id, user)));
    }

}
