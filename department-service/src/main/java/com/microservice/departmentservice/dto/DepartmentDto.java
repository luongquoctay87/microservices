package com.microservice.departmentservice.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class DepartmentDto implements Serializable {
    private Long departmentId;
    private String departmentName;
    private String departmentAddress;
    private String departmentCode;
}