package com.microservice.departmentservice.api.form;

import lombok.Data;

@Data
public class DepartmentForm {
    private Long departmentId;
    private String departmentName;
    private String departmentAddress;
    private String departmentCode;
}
