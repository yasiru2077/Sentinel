package com.example.sentinel.repository;

import com.example.sentinel.entity.Project;
import com.example.sentinel.entity.Stage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StageRepository extends JpaRepository<Stage, UUID> {
    List<Stage> findByProjectOrderByOrderIndexAsc(Project project);
    Optional<Stage> findByIdAndProject(UUID id, Project project);

    @Query("SELECT COALESCE(MAX(s.orderIndex), -1) FROM Stage s WHERE s.project = :project")
    Integer findMaxOrderIndex(@Param("project") Project project);
}
