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

    @Override
    public Task addNewTask(TaskForm taskForm, String token) {
        log.info("TaskService -> addNewTask");

        String token1 = token.substring(7);
        String redisResult = redisRepository.findResdisData(token1);
        ObjectMapper mapper = new ObjectMapper();

        ResultClass resultClass = null;
        try {
            resultClass = mapper.readValue(redisResult, ResultClass.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Long id = resultClass.getUserId();
        int userId = id.intValue();
        validateTaskForm(taskForm);

        Task task = Task.builder()
                .name(taskForm.getName())
                .assignee(taskForm.getAssignee())
                .created_by(userId)
                .description(taskForm.getJobDescription())
                .priority(PriorityEnums.valueOf(taskForm.getPriority()))
                .status(StatusEnums.valueOf(taskForm.getStatus()))
                .estimate_time(taskForm.getEstimate_time())
                .start_date(new Timestamp(taskForm.getStartDay()))
                .end_date(new Timestamp(taskForm.getEndDay()))
                .created_date(new Timestamp(System.currentTimeMillis()))
                .build();
        taskRepository.save(task);

        if (taskForm.getSectionId() != null) {
            Section section = sectionRepository.findById(taskForm.getSectionId()).get();
            if (!ObjectUtils.isEmpty(section)) {
                task.setSection_id(taskForm.getSectionId());
            }
        }

        if (taskForm.getProjectId() != null) {
            Project project = projectRepository.findById(taskForm.getProjectId()).get();
            if (!ObjectUtils.isEmpty(project)) {
                task.setProject_id(taskForm.getProjectId());
            }
        }

        if (taskForm.getParentId() != 0) {
            Task parent = taskRepository.findById(taskForm.getParentId()).get();
            if (!ObjectUtils.isEmpty(parent)) {
                task.setParent_id(taskForm.getParentId());
            }
        }

        taskRepository.save(task);
        return task;
    }

    @Override
    public Task getById(int taskId) {
        log.info("TaskService -> getById");

        Task task = taskRepository.findById(taskId).get();

        if (ObjectUtils.isEmpty(task)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy công việc");
        } else {
            return task;
        }
    }

    @Override
    public Task updateTask(TaskForm taskForm, int taskId) {
        log.info("TaskService -> updateTask");

        validateUpdateTask(taskForm, taskId);

        Task task = taskRepository.findById(taskId).get();

        if (task != null) {
            task.setName(taskForm.getName());
            task.setDescription(taskForm.getJobDescription());
            task.setAssignee(taskForm.getAssignee());
            task.setUpdated_date(new Timestamp(System.currentTimeMillis()));
            task.setStart_date(new Timestamp(taskForm.getStartDay()));
            task.setEnd_date(new Timestamp(taskForm.getEndDay()));
            task.setPriority(PriorityEnums.valueOf(taskForm.getPriority()));
            task.setStatus(StatusEnums.valueOf(taskForm.getStatus()));
            task.setEstimate_time((taskForm.getEstimate_time()));
            taskRepository.save(task);
        }
        return task;
    }

    @Override
    public Task updateStatus(int id, String status) {
        log.info("TaskService -> updateStatus");

        Task task = taskRepository.findById(id).get();
        if (ObjectUtils.isEmpty(task)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy công việc");
        }
        task.setStatus(StatusEnums.valueOf(status));
        task.setUpdated_date(new Timestamp(System.currentTimeMillis()));
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getListTask(Integer projectId, Integer sectionId) {
        log.info("TaskService -> getListTask");

        Project project = null;
        Section section = null;

        if (projectId != null) {
            if (!projectRepository.existsById(projectId)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy dự án");
            }
            project = projectRepository.findById(projectId).get();

        }
        if (sectionId != 0) {
            if (!sectionRepository.existsById(sectionId)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy giai đoạn");
            }
            section = sectionRepository.findById(sectionId).get();

        }

        List<Task> tasks = new ArrayList<>();

        if (!ObjectUtils.isEmpty(project) && !ObjectUtils.isEmpty(section)) {
            tasks = taskRepository.getListTaskInSectionAndProject(projectId, sectionId);

        } else if (ObjectUtils.isEmpty(section) && !ObjectUtils.isEmpty(project)) {
            tasks = taskRepository.findByProjectId(projectId);

        } else if (ObjectUtils.isEmpty(project) && ObjectUtils.isEmpty(section)) {
            tasks = taskRepository.getListTaskPersonal();
        }

        if (CollectionUtils.isEmpty(tasks)) {
            return Collections.EMPTY_LIST;
        }
        return tasks;
    }

    @Override
    public void deleteTask(int taskId) {
        log.info("TaskService -> deleteTask");

        Task task = taskRepository.findById(taskId).get();
        if (ObjectUtils.isEmpty(task)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy công việc");
        }

        taskRepository.deleteById(taskId);
    }

    private void validateTaskForm(TaskForm taskForm) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Tên dự án", taskForm.getName());
        map.put("Mô tả dự án", taskForm.getJobDescription());
        ValidateUtils.validateNullOrBlankString(map);

        if (taskForm.getProjectId() != null) {
            if (!projectRepository.existsById(taskForm.getProjectId())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy dự án");
            }
        }

    }

    private void validateUpdateTask(TaskForm taskForm, int taskId) {

        HashMap<String, String> map = new HashMap<>();
        map.put("Tên dự án", taskForm.getName());
        map.put("Mô tả dự án", taskForm.getJobDescription());
        ValidateUtils.validateNullOrBlankString(map);

        if (!taskRepository.existsById(taskId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy công việc");
        }
    }
}