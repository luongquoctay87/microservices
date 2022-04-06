package com.microservice.departmentservice.api.form;

import lombok.Data;

import java.sql.Timestamp;

@Data
public class DepartmentForm {
    private Long departmentId;
    private String departmentName;
}
