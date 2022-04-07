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

    @Column
    private Timestamp created_date;

    @Column
    private Timestamp updated_date;

    public UserDto toDto() {
        return UserDto.builder()
                .userId(userId)
                .username(username)
                .created_date(created_date)
                .updated_date(updated_date)
                .build();
    }
}
