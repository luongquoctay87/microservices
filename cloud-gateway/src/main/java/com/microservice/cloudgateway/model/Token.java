package com.microservice.cloudgateway.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class Token implements Serializable{
    private String token;
    private String username;
    private String roles;
    private long currentDate;
    private long expireTime;
}
