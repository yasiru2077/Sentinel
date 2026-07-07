package com.example.sentinel.repository;

import com.example.sentinel.entity.Company;
import com.example.sentinel.entity.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProjectRepository extends JpaRepository<Project, UUID> {
    List<Project> findByCompany(Company company);
}
