package com.example.sentinel.service;

import com.example.sentinel.dto.request.CreateProjectRequest;
import com.example.sentinel.dto.response.ProjectResponse;
import com.example.sentinel.entity.Company;
import com.example.sentinel.entity.Project;
import com.example.sentinel.entity.ProjectMember;
import com.example.sentinel.entity.User;
import com.example.sentinel.exception.ResourceNotFoundException;
import com.example.sentinel.repository.CompanyRepository;
import com.example.sentinel.repository.ProjectMemberRepository;
import com.example.sentinel.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;
    private final CompanyRepository companyRepository;
    private final ProjectMemberRepository projectMemberRepository;
    private final AccessControlService accessControlService;

    @Transactional
    public ProjectResponse create(UUID companyId, CreateProjectRequest request, User admin) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        accessControlService.requireAdmin(company, admin);

        Project project = Project.builder()
                .company(company)
                .createdBy(admin)
                .projectName(request.projectName())
                .description(request.description())
                .build();
        projectRepository.save(project);

        return ProjectResponse.of(project);
    }

    @Transactional(readOnly = true)
    public List<ProjectResponse> getByCompany(UUID companyId, User user) {
        Company company = companyRepository.findById(companyId)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        accessControlService.requireMember(company, user);

        List<Project> projects;
        if (accessControlService.isAdmin(company, user)) {
            projects = projectRepository.findByCompany(company);
        } else {
            projects = projectMemberRepository.findByUser(user)
                    .stream()
                    .map(ProjectMember::getProject)
                    .filter(p -> p.getCompany().getId().equals(companyId))
                    .toList();
        }

        return projects.stream().map(ProjectResponse::of).toList();
    }

    @Transactional(readOnly = true)
    public ProjectResponse getById(UUID projectId, User user) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        accessControlService.requireProjectAccess(project, user);

        return ProjectResponse.of(project);
    }

}
