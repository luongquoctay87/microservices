package com.microservice.departmentservice.entity;

import com.microservice.departmentservice.dto.DepartmentDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "pa_departments")
public class Department {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long departmentId;

    @Column(name = "name")
    private String departmentName;

    @Column(name = "enabled")
    private Boolean enabled;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "update_date")
    private Timestamp updatedDate;

    public DepartmentDto toDto() {
        return DepartmentDto.builder()
                .departmentId(departmentId)
                .departmentName(departmentName)
                .enabled(enabled)
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .build();
    }
}
