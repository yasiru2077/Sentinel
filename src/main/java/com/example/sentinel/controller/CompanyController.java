package com.example.sentinel.controller;

import com.example.sentinel.dto.request.CreateCompanyRequest;
import com.example.sentinel.dto.response.ApiResponse;
import com.example.sentinel.dto.response.CompanyResponse;
import com.example.sentinel.dto.response.RoleResponse;
import com.example.sentinel.entity.User;
import com.example.sentinel.service.CompanyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final CompanyService companyService;

    @PostMapping
    public ResponseEntity<ApiResponse<CompanyResponse>> create(
            @Valid @RequestBody CreateCompanyRequest request,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success(companyService.create(request, user), "Company created successfully"));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CompanyResponse>>> getMyCompanies(
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(ApiResponse.success(companyService.getMyCompanies(user)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyResponse>> getById(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(ApiResponse.success(companyService.getById(id, user)));
    }

    @GetMapping("/{id}/members")
    public ResponseEntity<ApiResponse<List<RoleResponse>>> getMembers(
            @PathVariable UUID id,
            @AuthenticationPrincipal User user
    ) {
        return ResponseEntity.ok(ApiResponse.success(companyService.getMembers(id, user)));
    }

}
