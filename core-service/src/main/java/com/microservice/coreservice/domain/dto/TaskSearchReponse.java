package com.microservice.coreservice.domain.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskSearchReponse {

    private Long id;
    private String name;
    private String assigneeName;
    private Long startDay;
    private Long endDay;
    private String priority;
    private String jobDescription;
    private String status;
    private Long parentId;
    private Long projectId;
    private String projectName;
    private Long sectionId;
    private String sectionName;
    private Long created_by;
    private float estimate_time;
    private Long created_date;
    private Long updated_date;
}
