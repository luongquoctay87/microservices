package com.microservice.coreservice.domain.form;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskForm {

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

}
