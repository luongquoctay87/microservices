package com.microservice.coreservice.service.impl;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.coreservice.constants.PriorityEnums;
import com.microservice.coreservice.constants.SortPropertyEnums;
import com.microservice.coreservice.constants.StatusEnums;
import com.microservice.coreservice.domain.dto.TaskSearchReponse;
import com.microservice.coreservice.domain.form.TaskForm;
import com.microservice.coreservice.domain.form.TaskSearchForm;
import com.microservice.coreservice.domain.model.ResultClass;
import com.microservice.coreservice.entity.Project;
import com.microservice.coreservice.entity.Section;
import com.microservice.coreservice.entity.Task;
import com.microservice.coreservice.repository.ProjectRepository;
import com.microservice.coreservice.repository.RedisRepository;
import com.microservice.coreservice.repository.SectionRepository;
import com.microservice.coreservice.repository.TaskRepository;
import com.microservice.coreservice.service.TaskService;
import com.microservice.coreservice.utils.ValidateUtils;
import com.microservice.coreservice.utils.pagination.PageResponse;
import com.microservice.coreservice.utils.pagination.Pages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private RedisRepository redisRepository;


    @Autowired
    private EntityManager entityManager;

}