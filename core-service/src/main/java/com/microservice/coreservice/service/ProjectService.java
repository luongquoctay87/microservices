package com.microservice.coreservice.service;

import com.microservice.coreservice.domain.form.ProjectForm;
import com.microservice.coreservice.entity.Project;

import java.util.List;

public interface ProjectService {

    Project addNewProject(ProjectForm form);

    Project update(ProjectForm form, Long projectId);

    Project updateName(String name, Long projectId);

    void deleteProject(Long projectId);

    Project findProjectById(Long projectId);

    List<Project> getListProject();
}
