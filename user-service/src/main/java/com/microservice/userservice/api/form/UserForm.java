package com.microservice.userservice.api.form;

import lombok.Data;

@Data
public class UserForm {
    private String username;
    private String password;
    private long departmentId;
}
