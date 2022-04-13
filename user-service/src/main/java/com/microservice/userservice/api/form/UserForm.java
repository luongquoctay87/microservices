package com.microservice.userservice.api.form;

import lombok.Data;

@Data
public class UserForm {
    private long departmentId;
    private String username;
    private String password;
}
