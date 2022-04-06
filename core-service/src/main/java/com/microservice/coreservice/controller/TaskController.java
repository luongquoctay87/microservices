package com.microservice.coreservice.controller;

import com.microservice.coreservice.domain.dto.TaskDto;
import com.microservice.coreservice.domain.dto.TaskSearchReponse;
import com.microservice.coreservice.domain.dto.TeamDto;
import com.microservice.coreservice.domain.form.TaskForm;
import com.microservice.coreservice.domain.form.TaskSearchForm;
import com.microservice.coreservice.domain.form.TeamForm;
import com.microservice.coreservice.entity.Task;
import com.microservice.coreservice.entity.Team;
import com.microservice.coreservice.service.TaskService;
import com.microservice.coreservice.utils.ApiResponse;
import com.microservice.coreservice.utils.pagination.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("tasks")
@Slf4j
public class TaskController {

    @Autowired
    private TaskService taskService;

}
