package com.yasiru.Sentinel.dto.response;

import com.yasiru.Sentinel.entity.CompanyPositions;

import java.time.Instant;

public record CompanyPositionResponse(
        Long id,
        String title,
        double hourly_rate,
        Long createdByAdminId,
        Instant createdAt,

        Instant updatedAt

) {


    public static CompanyPositionResponse from(CompanyPositions companyPositions){

        return new CompanyPositionResponse(
                companyPositions.getId(),
                companyPositions.getTitle(),
                companyPositions.getHourlyRate(),
                companyPositions.getCreatedByAdmin().getId(),
                companyPositions.getCreatedAt(),
                companyPositions.getUpdatedAt()
        );

    }

}
