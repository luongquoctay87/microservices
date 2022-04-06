package com.microservice.coreservice.controller;

import com.microservice.coreservice.domain.dto.ProjectDto;
import com.microservice.coreservice.domain.form.ProjectForm;
import com.microservice.coreservice.entity.Project;
import com.microservice.coreservice.service.ProjectService;
import com.microservice.coreservice.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/projects")
@Slf4j
public class ProjectController {

    @Autowired
    private ProjectService projectService;


    @PostMapping()
    public ResponseEntity<ApiResponse> createNewProject(@RequestBody ProjectForm _form) {
        log.info("ProjectControler -> createNewProject");

        Project project = projectService.addNewProject(_form);
        ProjectDto data = ProjectDto.toDto(project);
        ApiResponse response = data != null ? ApiResponse.appendSuccess(data, HttpStatus.CREATED.value(), "Thêm mới dự án thành công")
                : ApiResponse.appendError(HttpStatus.NO_CONTENT.value(), "Thêm mới dự án thất bại");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findProjectById(@PathVariable(name = "id") int _projectId) {
        log.info("ProjectControler -> findProjectById");

        Project project = projectService.findProjectById(_projectId);
        ProjectDto data = ProjectDto.toDto(project);
        ApiResponse response = data != null ? ApiResponse.appendSuccess(data, HttpStatus.OK.value(), null)
                : ApiResponse.appendError(HttpStatus.NO_CONTENT.value(), null);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getProjectLists() {
        log.info("ProjectControler -> getProjectLists");

        List<Project> project = projectService.getListProject();
        List<ProjectDto> data = project.stream().map(ProjectDto::toDto).collect(Collectors.toList());
        ApiResponse response = data != null ? ApiResponse.appendSuccess(data, HttpStatus.OK.value(), null)
                : ApiResponse.appendError(HttpStatus.NO_CONTENT.value(), null);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteProject(@PathVariable(name = "id") int _projectId) {
        log.info("ProjectControler -> deleteProject");

        projectService.deleteProject(_projectId);
        ApiResponse response = ApiResponse.appendSuccess(null, HttpStatus.OK.value(), "Xóa dự án thành công");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateProject(@RequestBody ProjectForm _form, @PathVariable(name = "id") int _projectId) {
        log.info("ProjectControler -> updateProject");

        Project project = projectService.update(_form, _projectId);
        ProjectDto data = ProjectDto.toDto(project);
        ApiResponse response = data != null ? ApiResponse.appendSuccess(data, HttpStatus.CREATED.value(), "Cập nhật dự án thành công")
                : ApiResponse.appendError(HttpStatus.NO_CONTENT.value(), "Cập nhật dự án thất bại");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateNameProject(@RequestParam(name = "name") String _projectName, @PathVariable(name = "id") int _projectId) {
        log.info("ProjectControler -> updateNameProject");

        Project project = projectService.updateName(_projectName, _projectId);
        ProjectDto data = ProjectDto.toDto(project);
        ApiResponse response = data != null ? ApiResponse.appendSuccess(data, HttpStatus.CREATED.value(), "Cập nhật dự án thành công")
                : ApiResponse.appendError(HttpStatus.NO_CONTENT.value(), "Cập nhật dự án thất bại");
        return ResponseEntity.ok(response);
    }
}
