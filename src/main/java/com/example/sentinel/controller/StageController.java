package com.example.sentinel.controller;

import com.example.sentinel.dto.request.CreateStageRequest;
import com.example.sentinel.dto.request.ReorderStageRequest;
import com.example.sentinel.dto.request.UpdateStageRequest;
import com.example.sentinel.dto.response.ApiResponse;
import com.example.sentinel.dto.response.StageResponse;
import com.example.sentinel.entity.User;
import com.example.sentinel.service.StageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/projects/{projectId}/stages")
@RequiredArgsConstructor
public class StageController {

    private final StageService stageService;

    @PostMapping
    public ResponseEntity<ApiResponse<StageResponse>> create(
            @PathVariable UUID projectId,
            @Valid @RequestBody CreateStageRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(stageService.create(projectId, request, user), "Stage created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<StageResponse>>> list(
            @PathVariable UUID projectId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(stageService.list(projectId, user)));
    }

    @PutMapping("/{stageId}")
    public ResponseEntity<ApiResponse<StageResponse>> rename(
            @PathVariable UUID projectId,
            @PathVariable UUID stageId,
            @Valid @RequestBody UpdateStageRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(stageService.rename(projectId, stageId, request, user), "Stage updated"));
    }

    @PutMapping("/{stageId}/reorder")
    public ResponseEntity<ApiResponse<StageResponse>> reorder(
            @PathVariable UUID projectId,
            @PathVariable UUID stageId,
            @Valid @RequestBody ReorderStageRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(stageService.reorder(projectId, stageId, request, user), "Stage reordered"));
    }

    @DeleteMapping("/{stageId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @PathVariable UUID projectId,
            @PathVariable UUID stageId,
            @AuthenticationPrincipal User user) {
        stageService.delete(projectId, stageId, user);
        return ResponseEntity.ok(ApiResponse.success(null, "Stage deleted"));
    }

}
