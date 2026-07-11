package com.example.sentinel.dto.response;

import com.example.sentinel.entity.JoinRequest;
import com.example.sentinel.entity.Status;

import java.time.Instant;
import java.util.UUID;

public record JoinRequestResponse(
        UUID id,
        UUID userId,
        String username,
        UUID companyId,
        String companyName,
        Status status,
        Instant createdAt
) {

    public static JoinRequestResponse of(JoinRequest joinRequest) {
        return new JoinRequestResponse(
                joinRequest.getId(),
                joinRequest.getUser().getId(),
                joinRequest.getUser().getUsername(),
                joinRequest.getCompany().getId(),
                joinRequest.getCompany().getCompanyName(),
                joinRequest.getStatus(),
                joinRequest.getCreatedAt()
        );
    }

}
