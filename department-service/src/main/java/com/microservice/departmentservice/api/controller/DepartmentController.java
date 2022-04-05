package com.microservice.departmentservice.api.controller;

import com.microservice.departmentservice.api.form.DepartmentForm;
import com.microservice.departmentservice.api.response.ApiResponse;
import com.microservice.departmentservice.dto.DepartmentDto;
import com.microservice.departmentservice.entity.Department;
import com.microservice.departmentservice.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping
    public ResponseEntity<ApiResponse> addDepartment(@RequestBody DepartmentForm _form) {
        Department department = departmentService.addNewDepartment(_form);
        DepartmentDto dto = department.toDto();
        ApiResponse response = ApiResponse.success(department, HttpStatus.OK.value(), "Thêm thành công");
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> findAllDepartments() {
        List<Department> departments = departmentService.getListDepartment();
        List<DepartmentDto> dto = departments.stream().map(Department::toDto).collect(Collectors.toList());
        ApiResponse response = ApiResponse.success(dto, HttpStatus.OK.value(), "Danh sách các phòng ban");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findDepartmentById(@PathVariable("id") long _id) {
        Department department = departmentService.getById(_id);
        DepartmentDto dto = department.toDto();
        ApiResponse response = ApiResponse.success(department,HttpStatus.OK.value(), String.format("Phòng ban %d",_id));
        return ResponseEntity.ok(response);
    }

    @PutMapping( "/{id}")
    public ResponseEntity<ApiResponse> updateDepartment(@RequestBody DepartmentForm _form, @PathVariable("id") Long _id) {
        Department department = departmentService.updateDepartment(_form.getDepartmentName(), _form.getDepartmentAddress(), _form.getDepartmentCode(), _id);
        DepartmentDto dto = department.toDto();
        ApiResponse response = ApiResponse.success(department,HttpStatus.OK.value(), "Chỉnh sửa thành công " + _id);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteDepartment(@PathVariable("id") long _id) {
        departmentService.deleteDepartment(_id);
        ApiResponse response = ApiResponse.success(null,HttpStatus.OK.value(), "Xóa thành công" + _id);
        return ResponseEntity.ok(response);
    }
}
