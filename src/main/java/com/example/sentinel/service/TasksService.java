package com.example.sentinel.service;

import com.example.sentinel.dto.request.TaskRequest;
import com.example.sentinel.dto.response.TaskResponse;
import com.example.sentinel.entity.Project;
import com.example.sentinel.entity.Stage;
import com.example.sentinel.entity.Task;
import com.example.sentinel.entity.User;
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
public class TasksService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final StageRepository stageRepository;
    private final AccessControlService accessControlService;

    @Transactional
    public TaskResponse create(UUID projectId, TaskRequest request, User user) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        accessControlService.requireProjectAccess(project, user);

        Stage stage = stageRepository.findByIdAndProject(request.stageId(), project)
                .orElseThrow(() -> new ResourceNotFoundException("Stage not found in this project"));

        Task task = Task.builder()
                .project(project)
                .stage(stage)
                .createdBy(user)
                .priority(request.priority())
                .title(request.title())
                .description(request.description())
                .build();

        return toResponse(taskRepository.save(task));
    }

    @Transactional(readOnly = true)
    public List<TaskResponse> getAll(UUID projectId, User user) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        accessControlService.requireProjectAccess(project, user);

        return taskRepository.findByProject(project)
                .stream().map(this::toResponse).toList();
    }

    @Transactional(readOnly = true)
    public TaskResponse getById(UUID projectId, UUID taskId, User user) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        accessControlService.requireProjectAccess(project, user);

        Task task = taskRepository.findByIdAndProject(taskId, project)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        return toResponse(task);
    }

    @Transactional
    public TaskResponse update(UUID projectId, UUID taskId, TaskRequest request, User user) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        accessControlService.requireProjectAccess(project, user);

        Task task = taskRepository.findByIdAndProject(taskId, project)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        Stage stage = stageRepository.findByIdAndProject(request.stageId(), project)
                .orElseThrow(() -> new ResourceNotFoundException("Stage not found in this project"));

        task.setTitle(request.title());
        task.setDescription(request.description());
        task.setPriority(request.priority());
        task.setStage(stage);

        return toResponse(taskRepository.save(task));
    }

    @Transactional
    public void delete(UUID projectId, UUID taskId, User user) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ResourceNotFoundException("Project not found"));

        accessControlService.requireProjectAccess(project, user);

        Task task = taskRepository.findByIdAndProject(taskId, project)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found"));

        taskRepository.delete(task);
    }

    private TaskResponse toResponse(Task task) {
        return new TaskResponse(
                task.getId(),
                task.getProject().getId(),
                task.getStage().getId(),
                task.getStage().getStageName(),
                task.getCreatedBy().getId(),
                task.getCreatedBy().getUsername(),
                task.getPriority(),
                task.getTitle(),
                task.getDescription(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }


}
