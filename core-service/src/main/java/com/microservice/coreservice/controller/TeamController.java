package com.microservice.coreservice.controller;

import com.microservice.coreservice.domain.dto.ProjectDto;
import com.microservice.coreservice.domain.dto.TeamDto;
import com.microservice.coreservice.domain.form.ProjectForm;
import com.microservice.coreservice.domain.form.TeamForm;
import com.microservice.coreservice.entity.Project;
import com.microservice.coreservice.entity.Team;
import com.microservice.coreservice.service.TeamService;
import com.microservice.coreservice.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teams")
@Slf4j
public class TeamController {

    @Autowired
    private TeamService teamService;


}
