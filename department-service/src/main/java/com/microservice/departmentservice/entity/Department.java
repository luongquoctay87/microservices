package com.microservice.departmentservice.entity;

import com.microservice.departmentservice.dto.DepartmentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long departmentId;
    private String departmentName;
    private String departmentAddress;
    private String departmentCode;

    public DepartmentDto toDto() {
        return DepartmentDto.builder()
                .departmentId(departmentId)
                .departmentName(departmentName)
                .departmentAddress(departmentAddress)
                .departmentCode(departmentCode)
                .build();
    }
}
