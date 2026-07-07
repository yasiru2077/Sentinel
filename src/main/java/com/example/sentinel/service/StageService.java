package com.example.sentinel.service;

import com.example.sentinel.dto.request.CreateStageRequest;
import com.example.sentinel.dto.request.ReorderStageRequest;
import com.example.sentinel.dto.request.UpdateStageRequest;
import com.example.sentinel.dto.response.StageResponse;
import com.example.sentinel.entity.Project;
import com.example.sentinel.entity.Stage;
import com.example.sentinel.entity.User;
import com.example.sentinel.exception.ConflictException;
import com.example.sentinel.exception.ResourceNotFoundException;
import com.example.sentinel.repository.ProjectRepository;
import com.example.sentinel.repository.StageRepository;
import com.example.sentinel.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StageService {

    private final StageRepository stageRepository;
    private final ProjectRepository projectRepository;
    private final TaskRepository taskRepository;
    private final AccessControlService accessControlService;

    @Transactional
    public StageResponse create(UUID projectId, CreateStageRequest request, User user) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        accessControlService.requireProjectAccess(project, user);

        int nextOrderIndex = stageRepository.findMaxOrderIndex(project) + 1;

        Stage stage = Stage.builder()
                .project(project)
                .createdBy(user)
                .stageName(request.stageName())
                .orderIndex(nextOrderIndex)
                .build();
        stageRepository.save(stage);

        return StageResponse.of(stage);
    }

    @Transactional(readOnly = true)
    public List<StageResponse> list(UUID projectId, User user) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        accessControlService.requireProjectAccess(project, user);

        return stageRepository.findByProjectOrderByOrderIndexAsc(project)
                .stream().map(StageResponse::of).toList();
    }

    @Transactional
    public StageResponse rename(UUID projectId, UUID stageId, UpdateStageRequest request, User user) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        accessControlService.requireProjectAccess(project, user);

        Stage stage = stageRepository.findByIdAndProject(stageId, project)
                .orElseThrow(() -> new ResourceNotFoundException("Stage not found"));

        stage.setStageName(request.stageName());
        stageRepository.save(stage);

        return StageResponse.of(stage);
    }

    @Transactional
    public StageResponse reorder(UUID projectId, UUID stageId, ReorderStageRequest request, User user) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        accessControlService.requireProjectAccess(project, user);

        Stage stage = stageRepository.findByIdAndProject(stageId, project)
                .orElseThrow(() -> new ResourceNotFoundException("Stage not found"));

        stage.setOrderIndex(request.orderIndex());
        stageRepository.save(stage);

        return StageResponse.of(stage);
    }

    @Transactional
    public void delete(UUID projectId, UUID stageId, User user) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        accessControlService.requireProjectAccess(project, user);

        Stage stage = stageRepository.findByIdAndProject(stageId, project)
                .orElseThrow(() -> new ResourceNotFoundException("Stage not found"));

        if (taskRepository.existsByStage(stage)) {
            throw new ConflictException("Cannot delete a stage that still has tasks in it");
        }

        stageRepository.delete(stage);
    }
}
