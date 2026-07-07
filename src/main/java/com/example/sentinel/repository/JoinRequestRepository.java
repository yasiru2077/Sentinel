package com.example.sentinel.repository;

import com.example.sentinel.entity.Company;
import com.example.sentinel.entity.JoinRequest;
import com.example.sentinel.entity.Status;
import com.example.sentinel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface JoinRequestRepository extends JpaRepository<JoinRequest, UUID> {

    Optional<JoinRequest> findByUserAndCompany(User user, Company company);

    List<JoinRequest> findByCompanyAndStatus(Company company, Status status);

}
