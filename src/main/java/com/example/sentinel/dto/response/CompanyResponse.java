package com.example.sentinel.dto.response;

import com.example.sentinel.entity.Company;

import java.time.Instant;
import java.util.UUID;

public record CompanyResponse(
        UUID id,
        String companyName,
        UUID createdById,
        Instant createdAt
) {

    public static CompanyResponse of(Company company) {
        return new CompanyResponse(
                company.getId(),
                company.getCompanyName(),
                company.getCreatedBy().getId(),
                company.getCreatedAt()
        );
    }

}
