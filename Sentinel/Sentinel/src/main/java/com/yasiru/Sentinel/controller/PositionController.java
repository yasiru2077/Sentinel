package com.yasiru.Sentinel.controller;

import com.yasiru.Sentinel.dto.request.CompanyPositionsRequest;
import com.yasiru.Sentinel.dto.response.CompanyPositionResponse;
import com.yasiru.Sentinel.entity.User;
import com.yasiru.Sentinel.service.CompanyPositionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/position")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class PositionController {

    private final CompanyPositionService companyPositionService;

    @GetMapping
    public ResponseEntity<Page<CompanyPositionResponse>> getAllPositions(
            @PageableDefault(size = 20,sort = "createdAt") Pageable pageable
            ){
            return ResponseEntity.ok(companyPositionService.getAllPositions(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CompanyPositionResponse> getPosition(@PathVariable Long id){
        return ResponseEntity.ok(companyPositionService.getPosition(id));
    }

    @PostMapping
    public ResponseEntity<CompanyPositionResponse> createPosition(
            @Valid @RequestBody CompanyPositionsRequest request,
            @AuthenticationPrincipal User admin
            ){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(companyPositionService.createPosition(request, admin.getId()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CompanyPositionResponse>  updatePosition(
     @Valid @RequestBody  CompanyPositionsRequest request,
     @PathVariable Long id,
     @AuthenticationPrincipal User admin
    ){
        return ResponseEntity.ok(
                companyPositionService.updatePosition(id,request,admin.getId())
        );
    }



}
