package com.microservice.departmentservice.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;

@Builder
@Data
public class DepartmentDto implements Serializable {
    private Long departmentId;
    private String departmentName;
    private Boolean enabled;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}