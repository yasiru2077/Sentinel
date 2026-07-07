package com.example.sentinel.repository;

import com.example.sentinel.entity.Company;
import com.example.sentinel.entity.Role;
import com.example.sentinel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {

    Optional<Role> findByUserAndCompany(User user, Company company);
    List<Role> findByCompany(Company company);
    List<Role> findByUser(User user);
    boolean existsByUserAndCompany(User user, Company company);

}
