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
                .created_date(new Timestamp(System.currentTimeMillis()))
                .type(ProjectEnums.valueOf(form.getType()))
                .build();
        projectRepository.save(project);

        ProjectTeam projectTeam = ProjectTeam.builder()
                .project_id(project.getId())
                .team_id(form.getTeamId())
                .created_date(new Timestamp(System.currentTimeMillis()))
                .build();

        projectTeamRepository.save(projectTeam);
        return project;
    }

    @Override
    public Project update(ProjectForm form, int projectId) {
        log.info("ProjectService -> update ");

        validateUpdateProject(form, projectId);

        Project project = projectRepository.findById(projectId).get();
        project.setName(form.getName());
        project.setDescription(form.getDescription());
        project.setType(ProjectEnums.valueOf(form.getType()));
        project.setEnabled(form.isEnabled());
        project.setUpdated_date(new Timestamp(System.currentTimeMillis()));

        if(form.getTeamId() != 0) {
            ProjectTeam projectTeam = (ProjectTeam) projectTeamRepository.findByTeamId(form.getTeamId());
            if(!ObjectUtils.isEmpty(projectTeam)) {
                projectTeam.setTeam_id(form.getTeamId());
                projectTeamRepository.save(projectTeam);
            }
        }
        return projectRepository.save(project);
    }

    @Override
    public Project updateName(String name, int projectId) {
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
        project.setCreated_date(new Timestamp(System.currentTimeMillis()));
        return projectRepository.save(project);
    }

    @Override
    public void deleteProject(int projectId) {
        log.info("ProjectService -> deleteProject ");

        if(!projectRepository.existsById(projectId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy dự án");
        }
        Project project = projectRepository.findById(projectId).get();
        project.setEnabled(false);
        projectRepository.save(project);
    }

    @Override
    public Project findProjectById(int projectId) {
        log.info("ProjectService -> findProjectById ");

        if(!projectRepository.existsById(projectId)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Không tìm thấy dự án");
        }
        return projectRepository.findById(projectId).get();
    }

    @Override
    public List<Project> getListProject() {
        log.info("ProjectService -> getListProject ");

        List<Project>  projects = (List<Project>) projectRepository.findAll();

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

    private void validateUpdateProject(ProjectForm form, int projectId) {
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
