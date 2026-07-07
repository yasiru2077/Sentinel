package com.example.sentinel.service;

import com.example.sentinel.dto.request.ApproveJoinRequestRequest;
import com.example.sentinel.dto.request.CreateJoinRequestRequest;
import com.example.sentinel.dto.response.JoinRequestResponse;
import com.example.sentinel.entity.*;
import com.example.sentinel.exception.ConflictException;
import com.example.sentinel.exception.ResourceNotFoundException;
import com.example.sentinel.exception.UnauthorizedException;
import com.example.sentinel.repository.CompanyRepository;
import com.example.sentinel.repository.JoinRequestRepository;
import com.example.sentinel.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JoinRequestService {

    private final JoinRequestRepository joinRequestRepository;
    private final CompanyRepository companyRepository;
    private final RoleRepository roleRepository;
    private final AccessControlService accessControlService;

    @Transactional
    public JoinRequestResponse create(CreateJoinRequestRequest request, User user) {
        if (user == null) throw new UnauthorizedException("Authentication required");

        Company company = companyRepository.findByCompanyName(request.companyName())
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        if (roleRepository.existsByUserAndCompany(user, company)) {
            throw new ConflictException("You are already a member of this company");
        }

        JoinRequest joinRequest = joinRequestRepository.findByUserAndCompany(user, company)
                .orElse(JoinRequest.builder().user(user).company(company).build());

        if (joinRequest.getStatus() == Status.PENDING) {
            throw new  ConflictException("You already have a pending request for this company");
        }

        joinRequest.setStatus(Status.PENDING);
        joinRequestRepository.save(joinRequest);

        return JoinRequestResponse.of(joinRequest);
    }

    @Transactional(readOnly = true)
    public List<JoinRequestResponse> getPending(UUID companyId, User admin) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        accessControlService.requireAdmin(company, admin);

        return joinRequestRepository.findByCompanyAndStatus(company, Status.PENDING)
                .stream().map(JoinRequestResponse::of).toList();
    }

    @Transactional
    public JoinRequestResponse approve(UUID requestId, ApproveJoinRequestRequest request, User admin) {
        JoinRequest joinRequest = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Join request not found"));

        accessControlService.requireAdmin(joinRequest.getCompany(), admin);

        if (joinRequest.getStatus() != Status.PENDING) {
            throw new ConflictException("This request has already been resolved");
        }

        joinRequest.setStatus(Status.APPROVED);
        joinRequestRepository.save(joinRequest);

        Role role = Role.builder()
                .user(joinRequest.getUser())
                .company(joinRequest.getCompany())
                .jobPosition(request.jobPosition())
                .roleType(RoleType.MEMBER)
                .build();
        roleRepository.save(role);

        return JoinRequestResponse.of(joinRequest);
    }

    @Transactional
    public JoinRequestResponse reject(UUID requestId, User admin) {
        JoinRequest joinRequest = joinRequestRepository.findById(requestId)
                .orElseThrow(() -> new ResourceNotFoundException("Join request not found"));

        accessControlService.requireAdmin(joinRequest.getCompany(), admin);

        if (joinRequest.getStatus() != Status.PENDING) {
            throw new ConflictException("This request has already been resolved");
        }

        joinRequest.setStatus(Status.REJECTED);
        joinRequestRepository.save(joinRequest);

        return JoinRequestResponse.of(joinRequest);
    }

}
