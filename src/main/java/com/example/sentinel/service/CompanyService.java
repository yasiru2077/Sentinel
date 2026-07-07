package com.example.sentinel.service;

import com.example.sentinel.dto.request.CreateCompanyRequest;
import com.example.sentinel.dto.response.CompanyResponse;
import com.example.sentinel.dto.response.RoleResponse;
import com.example.sentinel.entity.Company;
import com.example.sentinel.entity.Role;
import com.example.sentinel.entity.RoleType;
import com.example.sentinel.entity.User;
import com.example.sentinel.exception.ConflictException;
import com.example.sentinel.exception.ResourceNotFoundException;
import com.example.sentinel.exception.UnauthorizedException;
import com.example.sentinel.repository.CompanyRepository;
import com.example.sentinel.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CompanyService {

    private final CompanyRepository companyRepository;
    private final RoleRepository roleRepository;
    private final AccessControlService accessControlService;

    @Transactional
    public CompanyResponse create(CreateCompanyRequest request, User user){

        if (user == null) throw new UnauthorizedException("Authentication required");

        if (companyRepository.existsCompanyName(request.companyName())){
            throw new ConflictException("Company name already taken");
        }

        Company company = Company.builder()
                .companyName(request.companyName())
                .createdBy(user)
                .build();

        companyRepository.save(company);

        Role adminRole = Role.builder()
                .user(user)
                .company(company)
                .jobPosition(request.jobPosition())
                .roleType(RoleType.ADMIN)
                .build();

        roleRepository.save(adminRole);

        return CompanyResponse.of(company);
    }

    @Transactional(readOnly = true)
    public List<CompanyResponse> getMyCompanies(User user){
        if (user == null) throw new UnauthorizedException("Authentication required");

        return roleRepository.findByUser(user)
                .stream()
                .map(role -> CompanyResponse.of(role.getCompany()))
                .toList();
    }

    @Transactional(readOnly = true)
    public CompanyResponse getById(UUID id, User user){
        if (user == null) throw new UnauthorizedException("Authentication required");

        Company company = companyRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("Company not found"));

        accessControlService.requireMember(company, user);
        return CompanyResponse.of(company);
    }

    @Transactional(readOnly = true)
    public List<RoleResponse> getMembers(UUID companyId, User user){
        if (user == null) throw new UnauthorizedException("Authentication required");

        Company company = companyRepository.findById(companyId)
                .orElseThrow(()->new ResourceNotFoundException("Company not found"));

        accessControlService.requireMember(company,user);

        return roleRepository.findByCompany(company)
                .stream().map(RoleResponse::of).toList();

    }

}
