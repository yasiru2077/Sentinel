package com.example.sentinel.service;

import com.example.sentinel.dto.request.AddProjectMemberRequest;
import com.example.sentinel.dto.response.ProjectMemberResponse;
import com.example.sentinel.entity.Project;
import com.example.sentinel.entity.ProjectMember;
import com.example.sentinel.entity.User;
import com.example.sentinel.exception.ConflictException;
import com.example.sentinel.exception.ResourceNotFoundException;
import com.example.sentinel.repository.ProjectMemberRepository;
import com.example.sentinel.repository.ProjectRepository;
import com.example.sentinel.repository.RoleRepository;
import com.example.sentinel.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectMemberService {

    private final ProjectMemberRepository projectMemberRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final AccessControlService accessControlService;

    @Transactional
    public ProjectMemberResponse add(UUID projectId, AddProjectMemberRequest request, User admin) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        accessControlService.requireAdmin(project.getCompany(), admin);

        User targetUser = userRepository.findById(request.userId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!roleRepository.existsByUserAndCompany(targetUser, project.getCompany())) {
            throw new ConflictException("User must be a member of the company before being added to a project");
        }

        if (projectMemberRepository.existsByProjectAndUser(project, targetUser)) {
            throw new ConflictException("User is already assigned to this project");
        }

        ProjectMember member = ProjectMember.builder()
                .project(project)
                .user(targetUser)
                .addedBy(admin)
                .build();
        projectMemberRepository.save(member);

        return ProjectMemberResponse.of(member);
    }

    @Transactional
    public void remove(UUID projectId, UUID userId, User admin) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        accessControlService.requireAdmin(project.getCompany(), admin);

        User targetUser = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        ProjectMember member = projectMemberRepository.findByProjectAndUser(project, targetUser)
                .orElseThrow(() -> new ResourceNotFoundException("User is not assigned to this project"));

        projectMemberRepository.delete(member);
    }

    @Transactional(readOnly = true)
    public List<ProjectMemberResponse> list(UUID projectId, User user) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        accessControlService.requireProjectAccess(project, user);

        return projectMemberRepository.findByProject(project)
                .stream().map(ProjectMemberResponse::of).toList();
    }


}
