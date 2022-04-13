package com.microservice.userservice.entity;

import com.microservice.userservice.dto.UserDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table(name = "pa_users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long userId;

    @Column(name = "username")
    private String username;

    @Column(name = "password")
    private String password;

    @Column(name = "department_id")
    private long departmentId;

    @Column(name = "creatd_date")
    private Timestamp createdDate;

    @Column(name = "updated_date")
    private Timestamp updatedDate;

    public UserDto toDto() {
        return UserDto.builder()
                .userId(userId)
                .username(username)
                .departmentId(departmentId)
                .createdDate(createdDate)
                .updatedDate(updatedDate)
                .build();
    }
}
