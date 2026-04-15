package com.yasiru.Sentinel.repository;

import com.yasiru.Sentinel.entity.CompanyPositions;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyPositionRepository extends JpaRepository <CompanyPositions,Long>{

    boolean existsByTitle(String title);
}
