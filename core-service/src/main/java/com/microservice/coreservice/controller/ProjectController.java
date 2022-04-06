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


}
