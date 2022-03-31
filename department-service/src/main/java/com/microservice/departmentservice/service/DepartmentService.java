package com.microservice.departmentservice.service;

import com.microservice.departmentservice.entity.Department;
import com.microservice.departmentservice.repository.DepartmentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class DepartmentService {

    @Autowired
    private DepartmentRepository departmentRepository;

    public Department saveDepartment(Department department) {
        log.info("DepartmentService -> saveDepartment");
        return departmentRepository.save(department);
    }

    public Department findByDepartmentId(Long id) {
        log.info("DepartmentService -> findByDepartmentId");
        return departmentRepository.findByDepartmentId(id);
    }
}
