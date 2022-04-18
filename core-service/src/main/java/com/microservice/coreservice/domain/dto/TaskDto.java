package com.microservice.coreservice.domain.dto;

import com.microservice.coreservice.entity.Task;
import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

@Data
@Builder
public class TaskDto {

    private Long id;
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
    private String created_date;
    private String updated_date;

    public static TaskDto toDto(Task task) {
        return TaskDto.builder()
                .id(task.getId())
                .name(task.getName())
                .assignee(task.getAssignee())
                .startDay(task.getStartDate().getTime())
                .endDay(task.getEndDate().getTime())
                .priority(task.getPriority().name())
                .jobDescription(task.getDescription())
                .status(task.getStatus().name())
                .parentId(task.getParentId())
                .projectId(task.getProjectId() != null ? task.getProjectId() : -1)
                .sectionId(task.getSectionId() != null ? task.getSectionId() : -1)
                .estimate_time(task.getEstimateTime())
                .created_date(task.getCreatedDate() != null ? String.valueOf(task.getCreatedDate()) : Strings.EMPTY)
                .updated_date(task.getUpdatedDate() != null ? String.valueOf(task.getUpdatedDate()) : Strings.EMPTY)
                .build();
    }
}
