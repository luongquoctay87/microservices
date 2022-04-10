package com.microservice.coreservice.service;

import com.microservice.coreservice.domain.dto.TaskSearchReponse;
import com.microservice.coreservice.domain.form.TaskForm;
import com.microservice.coreservice.domain.form.TaskSearchForm;
import com.microservice.coreservice.entity.Task;
import com.microservice.coreservice.utils.pagination.PageResponse;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface TaskService {

    PageResponse<TaskSearchReponse> search(int page,
                                           int pageSize,
                                           TaskSearchForm form
    );

    Task addNewTask(TaskForm form, String token);

    Task getById(Long taskId);

    Task updateTask(TaskForm form, Long taskId);

    Task updateStatus(Long id, String status);

    List<Task> getListTask(Long projectId, Long sectionId);

    void deleteTask(Long taskId);

    ByteArrayInputStream exportProgressUser();

    ByteArrayInputStream exportProgressTeam();

}
