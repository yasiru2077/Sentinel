package com.example.sentinel.controller;

import com.example.sentinel.dto.request.AddProjectMemberRequest;
import com.example.sentinel.dto.response.ApiResponse;
import com.example.sentinel.dto.response.ProjectMemberResponse;
import com.example.sentinel.entity.User;
import com.example.sentinel.service.ProjectMemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projectId}/members")
@RequiredArgsConstructor
public class ProjectMemberController {

    private final ProjectMemberService projectMemberService;

    @PostMapping
    public ResponseEntity<ApiResponse<ProjectMemberResponse>> add(
            @PathVariable UUID projectId,
            @Valid @RequestBody AddProjectMemberRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(projectMemberService.add(projectId, request, user), "Member added to project"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<ProjectMemberResponse>>> list(
            @PathVariable UUID projectId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(projectMemberService.list(projectId, user)));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponse<Void>> remove(
            @PathVariable UUID projectId,
            @PathVariable UUID userId,
            @AuthenticationPrincipal User user) {
        projectMemberService.remove(projectId, userId, user);
        return ResponseEntity.ok(ApiResponse.success(null, "Member removed from project"));
    }

}
