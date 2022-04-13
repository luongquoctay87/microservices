package com.microservice.userservice.dto;

import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import java.io.Serializable;
import java.sql.Timestamp;

@Builder
@Data
public class UserDto implements Serializable {
    private Long userId;
    private long departmentId;
    private String username;
    private String password;
    private Timestamp createdDate;
    private Timestamp updatedDate;
}
