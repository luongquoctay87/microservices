package com.microservice.authserver.entity;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Transient;

import java.io.Serializable;
import java.util.List;

@Builder
@Data
public class Token implements Serializable {
    private String token;
    private String username;
    private String roles;
    @Transient
    private List<Activity> activities;

    @Transient
    private Long userId;
    private long currentDate;
    private long expireTime;
}
