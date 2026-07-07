package com.example.sentinel.repository;

import com.example.sentinel.entity.Project;
import com.example.sentinel.entity.ProjectMember;
import com.example.sentinel.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProjectMemberRepository extends JpaRepository<ProjectMember, UUID> {

    boolean existsByProjectAndUser(Project project, User user);
    Optional<ProjectMember> findByProjectAndUser(Project project,User user);
    List<ProjectMember> findByProject(Project project);
    List<ProjectMember> findByUser(User user);

}
