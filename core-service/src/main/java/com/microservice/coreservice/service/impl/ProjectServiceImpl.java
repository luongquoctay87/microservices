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


}
