package com.microservice.coreservice.service.impl;

import com.microservice.coreservice.domain.form.TeamForm;
import com.microservice.coreservice.entity.ProjectTeam;
import com.microservice.coreservice.entity.Team;
import com.microservice.coreservice.entity.TeamUser;
import com.microservice.coreservice.repository.ProjectTeamRepository;
import com.microservice.coreservice.repository.TeamRepository;
import com.microservice.coreservice.repository.TeamUserRepository;
import com.microservice.coreservice.service.TeamService;
import com.microservice.coreservice.utils.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamUserRepository teamUserRepository;

    @Autowired
    private ProjectTeamRepository projectTeamRepository;


}
