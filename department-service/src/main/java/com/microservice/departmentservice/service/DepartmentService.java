package com.microservice.departmentservice.service;

import com.microservice.departmentservice.api.form.DepartmentForm;
import com.microservice.departmentservice.entity.Department;

import java.util.List;

public interface DepartmentService {
    Department saveDepartment(DepartmentForm form);

    Department updateDepartment(String name, Long id);

    void deleteDepartment(Long id);

    Department getById(Long id);

    List<Department> getListDepartment();

    Department save(Department department);
}
