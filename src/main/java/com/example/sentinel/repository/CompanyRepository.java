package com.example.sentinel.repository;

import com.example.sentinel.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, UUID> {

    boolean existsCompanyName(String companyName);
    Optional<Company> findByCompanyName(String companyName);

}
