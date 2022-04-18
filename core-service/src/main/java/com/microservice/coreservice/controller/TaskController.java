package com.microservice.coreservice.controller;

import com.microservice.coreservice.domain.dto.TaskDto;
import com.microservice.coreservice.domain.dto.TaskSearchReponse;
import com.microservice.coreservice.domain.form.ColumExcel;
import com.microservice.coreservice.domain.form.TaskForm;
import com.microservice.coreservice.domain.form.TaskSearchForm;
import com.microservice.coreservice.entity.Task;
import com.microservice.coreservice.service.TaskService;
import com.microservice.coreservice.utils.ApiResponse;
import com.microservice.coreservice.utils.pagination.PageResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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

    @PostMapping("/search")
    public ResponseEntity<ApiResponse> search(@RequestBody TaskSearchForm _form,
                                              @RequestParam(name = "page", defaultValue = "1") int _page,
                                              @RequestParam(name = "pageSize", defaultValue = "20") int _pageSize) {
        log.info("Task Controler -> search");

        PageResponse<TaskSearchReponse> data = taskService.search(_page, _pageSize, _form);
        ApiResponse response = data != null ? ApiResponse.appendSuccess(data, HttpStatus.OK.value(), null)
                : ApiResponse.appendError(HttpStatus.NO_CONTENT.value(), null);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<ApiResponse> createNewTask(@RequestBody TaskForm _form, @RequestHeader(name = "Authorization") String _token) {
        log.info("Task Controler -> createNewTask");

        Task task = taskService.addNewTask(_form, _token);
        TaskDto data = TaskDto.toDto(task);
        ApiResponse response = data != null ? ApiResponse.appendSuccess(data, HttpStatus.CREATED.value(), "Thêm mới công việc thành công")
                : ApiResponse.appendError(HttpStatus.NO_CONTENT.value(), "Thêm mới công việc thất bại");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getTaskById(@PathVariable(name = "id") Long _taskId) {
        log.info("Task Controler -> getTaskById");

        Task task = taskService.getById(_taskId);
        TaskDto data = TaskDto.toDto(task);
        ApiResponse response = data != null ? ApiResponse.appendSuccess(data, HttpStatus.OK.value(), null)
                : ApiResponse.appendError(HttpStatus.NO_CONTENT.value(), null);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getListTask(@RequestParam(name = "projectId", required = false) Long _projectId, @RequestParam(name = "sectionId", required = false) Long _sectionId) {
        log.info("Task Controler -> getListTask");

        List<Task> tasks = taskService.getListTask(_projectId, _sectionId);
        List<TaskDto> data = tasks.stream().map(TaskDto::toDto).collect(Collectors.toList());
        ApiResponse response = data != null ? ApiResponse.appendSuccess(data, HttpStatus.OK.value(), null)
                : ApiResponse.appendError(HttpStatus.NO_CONTENT.value(), "Error");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> changeStatus(@PathVariable(name = "id") Long _id, @RequestParam(name = "status") String _status) {
        log.info("Task Controler -> updateStatus");

        Task task = taskService.updateStatus(_id, _status);
        TaskDto data = TaskDto.toDto(task);
        ApiResponse response = data != null ? ApiResponse.appendSuccess(data, HttpStatus.OK.value(), "Cập nhật trạng thái công việc thành công")
                : ApiResponse.appendError(HttpStatus.BAD_REQUEST.value(), "Cập nhật trạng thái công việc không thành công");
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable(name = "id") Long _id) {
        log.info("Task Controler -> deleteTask");

        taskService.deleteTask(_id);
        ApiResponse response = ApiResponse.appendSuccess(null, HttpStatus.OK.value(), "Xóa công việc thành công");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateTask(@RequestBody TaskForm _form, @PathVariable(name = "id") Long _taskId) {
        log.info("Task Controler -> updateTask");

        Task task = taskService.updateTask(_form, _taskId);
        TaskDto data = TaskDto.toDto(task);
        ApiResponse response = data != null ? ApiResponse.appendSuccess(data, HttpStatus.CREATED.value(), "Cập nhật công việc thành công")
                : ApiResponse.appendError(HttpStatus.NO_CONTENT.value(), "Cập nhật công việc thất bại");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/export-users")
    public ResponseEntity<?> exportProgressUserToExcel(@RequestBody ColumExcel columExcel) {
        String filename = "users.xlsx";
        InputStreamResource file = new InputStreamResource(taskService.exportProgressUser(columExcel));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }

    @PostMapping("/export-teams")
    public ResponseEntity<?> exportProgressTeamToExcel(@RequestBody ColumExcel columExcel) {
        String filename = "teams.xlsx";
        InputStreamResource file = new InputStreamResource(taskService.exportProgressTeam(columExcel));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + filename)
                .contentType(MediaType.parseMediaType("application/vnd.ms-excel"))
                .body(file);
    }
}
