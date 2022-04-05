package com.microservice.departmentservice.service.Impl;

import com.microservice.departmentservice.api.form.DepartmentForm;
import com.microservice.departmentservice.entity.Department;
import com.microservice.departmentservice.repository.DepartmentRepository;
import com.microservice.departmentservice.service.DepartmentService;
import com.microservice.departmentservice.ultis.exception.BadRequestException;
import com.microservice.departmentservice.ultis.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;

@Service
public class DepartmentServiceImpl implements DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Override
    public Department addNewDepartment(DepartmentForm form) {
        if(!StringUtils.hasText(form.getDepartmentName())) {
            String mess = "Invalid argument";
            throw new BadRequestException(mess);
        }

        Department department = departmentRepository.findByDepartmentName(form.getDepartmentName());

        if(!ObjectUtils.isEmpty(department)) {
            String mess = "department-not-exits";
            throw new NotFoundException(mess);
        }

        department = Department.builder()
                .departmentName(form.getDepartmentName())
                .departmentAddress(form.getDepartmentAddress())
                .departmentCode(form.getDepartmentCode())
                .build();

        return save(department);
    }

    @Override
    public Department updateDepartment(String name, String address, String code, Long id) {
        Department department = departmentRepository.findById(id).get();

        if(ObjectUtils.isEmpty(department)) {
            String mess = "department-not-exits";
            throw new NotFoundException(mess);
        }

        Department department1 = departmentRepository.findByDepartmentName(name);

        if(!ObjectUtils.isEmpty(department1)) {
            String mess = "department-not-exits";
            throw new NotFoundException(mess);
        }

        department.setDepartmentName(name);

        return save(department);
    }

    @Override
    public void deleteDepartment(Long id) {
        Department department = departmentRepository.findById(id).get();

        if(ObjectUtils.isEmpty(department)) {
            String mess = "department-not-exits";
            throw new NotFoundException(mess);
        }

        departmentRepository.deleteById(id);
    }

    @Override
    public Department getById(Long id) {
        Department department = departmentRepository.findById(id).get();

        if(ObjectUtils.isEmpty(department)) {
            String mess = "department-not-exits";
            throw new NotFoundException(mess);
        }

        return department;
    }

    @Override
    public List<Department> getListDepartment() {
        List<Department> departments = (List<Department>) departmentRepository.findAll();
        if(ObjectUtils.isEmpty(departments)) {
            return Collections.EMPTY_LIST;
        }

        return departments;
    }

    @Override
    public Department save(Department department) {
        return departmentRepository.save(department);
    }
}
