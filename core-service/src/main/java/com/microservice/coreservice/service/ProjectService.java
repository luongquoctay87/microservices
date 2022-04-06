package com.microservice.coreservice.service;

import com.microservice.coreservice.domain.form.ProjectForm;
import com.microservice.coreservice.entity.Project;

import java.util.List;

public interface ProjectService {

    Project addNewProject(ProjectForm form);

    Project update(ProjectForm form, int projectId);

    Project updateName(String name, int projectId);

    void deleteProject(int projectId);

    Project findProjectById(int projectId);

    List<Project> getListProject();

}
