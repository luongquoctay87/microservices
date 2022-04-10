package com.microservice.coreservice.domain.dto;

import com.microservice.coreservice.entity.Task;
import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.sql.Timestamp;

@Data
@Builder
public class TaskDto {

    private String name;
    private Long assignee;
    private Long startDay;
    private Long endDay;
    private String priority;
    private String jobDescription;
    private String status;
    private Long parentId;
    private Long projectId;
    private Long sectionId;
    private float estimate_time;
    private Long created_date;
    private Long updated_date;

    public static TaskDto toDto(Task task) {
        return TaskDto.builder()
                .name(task.getName())
                .assignee(task.getAssignee())
                .startDay(task.getStart_date().getTime())
                .endDay(task.getEnd_date().getTime())
                .priority(task.getPriority().name())
                .jobDescription(task.getDescription())
                .status(task.getStatus().name())
                .parentId(task.getParent_id())
                .projectId(task.getProject_id() != null ? task.getProject_id() : null)
                .sectionId(task.getSection_id() != null ? task.getSection_id() : null)
                .estimate_time(task.getEstimate_time())
                .created_date(task.getCreated_date().getTime())
                .updated_date(task.getUpdated_date() != null ? task.getUpdated_date().getTime() : null)
                .build();
    }
}
