package com.example.sentinel.controller;

import com.example.sentinel.dto.request.ApproveJoinRequestRequest;
import com.example.sentinel.dto.request.CreateJoinRequestRequest;
import com.example.sentinel.dto.response.ApiResponse;
import com.example.sentinel.dto.response.JoinRequestResponse;
import com.example.sentinel.entity.User;
import com.example.sentinel.service.JoinRequestService;
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
public class JoinRequestController {


    private final JoinRequestService joinRequestService;

    @PostMapping("/api/join-requests")
    public ResponseEntity<ApiResponse<JoinRequestResponse>> create(
            @Valid @RequestBody CreateJoinRequestRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(joinRequestService.create(request, user), "Join request submitted"));
    }

    @GetMapping("/api/companies/{companyId}/join-requests")
    public ResponseEntity<ApiResponse<List<JoinRequestResponse>>> getPending(
            @PathVariable UUID companyId,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(joinRequestService.getPending(companyId, user)));
    }

    @PutMapping("/api/join-requests/{id}/approve")
    public ResponseEntity<ApiResponse<JoinRequestResponse>> approve(
            @PathVariable UUID id,
            @Valid @RequestBody ApproveJoinRequestRequest request,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(joinRequestService.approve(id, request, user), "Join request approved"));
    }

    @PutMapping("/api/join-requests/{id}/reject")
    public ResponseEntity<ApiResponse<JoinRequestResponse>> reject(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user) {
        return ResponseEntity.ok(ApiResponse.success(joinRequestService.reject(id, user), "Join request rejected"));
    }

}
