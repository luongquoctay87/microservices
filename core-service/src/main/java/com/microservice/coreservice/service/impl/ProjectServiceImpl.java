package com.microservice.coreservice.service.impl;

import com.microservice.coreservice.constants.ProjectEnums;
import com.microservice.coreservice.domain.form.ProjectForm;
import com.microservice.coreservice.entity.Project;
import com.microservice.coreservice.entity.ProjectTeam;
import com.microservice.coreservice.repository.ProjectRepository;
import com.microservice.coreservice.repository.ProjectTeamRepository;
import com.microservice.coreservice.service.ProjectService;
import com.microservice.coreservice.utils.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;
import java.sql.Timestamp;
import java.util.*;

@Service
@Slf4j
public class ProjectServiceImpl implements ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private ProjectTeamRepository projectTeamRepository;

    @Override
    public Project addNewProject(ProjectForm form) {
        log.info("ProjectService -> addNewProject ");

        validateProjectForm(form);

        Project project = Project.builder()
                .name(form.getName())
                .description(form.getDescription())
                .enabled(Boolean.TRUE)
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .type(ProjectEnums.valueOf(form.getType()))
                .build();
        projectRepository.save(project);

        ProjectTeam projectTeam = ProjectTeam.builder()
                .projectId(project.getId())
                .teamId(form.getTeamId())
                .createdDate(new Timestamp(System.currentTimeMillis()))
                .build();

        projectTeamRepository.save(projectTeam);
        return project;
    }

    @Override
    public Project update(ProjectForm form, Long projectId) {
        log.info("ProjectService -> update ");

        validateUpdateProject(form, projectId);

        Project project = projectRepository.findById(projectId).get();
        project.setName(form.getName() != null ? form.getName() : project.getName());
        project.setDescription(form.getDescription() != null ? form.getDescription() : project.getDescription());
        project.setType( form.getType() != null ? ProjectEnums.valueOf(form.getType()) : project.getType() );
        project.setEnabled(form.getEnabled() != null ? form.getEnabled() : project.getEnabled());
        project.setUpdatedDate(new Timestamp(System.currentTimeMillis()));

        if(form.getTeamId() != null) {
            ProjectTeam projectTeam = projectTeamRepository.findByTeamId(form.getTeamId());
            if(!ObjectUtils.isEmpty(projectTeam)) {
                projectTeam.setTeamId(form.getTeamId());
                projectTeamRepository.save(projectTeam);
            }
        }

        return projectRepository.save(project);
    }

    @Override
    public Project updateName(String name, Long projectId) {
        log.info("ProjectService -> updateName ");

        ValidateUtils.validateNullOrBlankString(name);

        if(projectRepository.existsByName(name)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tên dự án đã tồn tại");
        }

        if(!projectRepository.existsById(projectId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy dự án");
        }

        Project project = projectRepository.findById(projectId).get();
        project.setName(name);
        project.setCreatedDate(new Timestamp(System.currentTimeMillis()));

        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long projectId) {
        log.info("ProjectService -> deleteProject ");

        if(!projectRepository.existsById(projectId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy dự án");
        }

        Project project = projectRepository.findById(projectId).get();
        project.setEnabled(false);

        projectRepository.save(project);
    }

    @Override
    public Project findProjectById(Long projectId) {
        log.info("ProjectService -> findProjectById ");

        if(!projectRepository.existsById(projectId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy dự án");
        }

        return projectRepository.findById(projectId).get();
    }

    @Override
    public List<Project> getListProject() {
        log.info("ProjectService -> getListProject ");

        List<Project>  projects =  projectRepository.findAll();

        if(!CollectionUtils.isEmpty(projects) && projects.size() > 0) {
            return projects;
        }

        return Collections.EMPTY_LIST;
    }

    private void validateProjectForm(ProjectForm form) {

        Map<String, String> map = new HashMap<>();
        map.put("name", form.getName());
        map.put("decription", form.getDescription());
        ValidateUtils.validateNullOrBlankString((HashMap<String, String>) map);

        if(projectRepository.existsByName(form.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tên dự án đã tồn tại");
        }
    }

    private void validateUpdateProject(ProjectForm form, Long projectId) {

        Map<String, String> map = new HashMap<>();
        map.put("name", form.getName());
        map.put("decription", form.getDescription());
        ValidateUtils.validateNullOrBlankString((HashMap<String, String>) map);

        if(projectRepository.existsByName(form.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Tên dự án đã tồn tại");
        }

        if(!projectRepository.existsById(projectId)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy dự án");
        }
    }
}