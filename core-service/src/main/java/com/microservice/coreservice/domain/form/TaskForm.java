package com.microservice.coreservice.domain.form;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TaskForm {

    private String name;

    private int assignee;
    private Long startDay;
    private Long endDay;
    private String priority;
    private String jobDescription;
    private String status;
    private int parentId;
    private Integer projectId;
    private Integer sectionId;
    private float estimate_time;

}
