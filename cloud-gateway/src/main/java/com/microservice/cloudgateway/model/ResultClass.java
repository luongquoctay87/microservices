package com.microservice.cloudgateway.model;

import lombok.Data;

import java.util.List;

@Data
public class ResultClass {
    private Long userId;
    private List<String> activities;
//    private Token token;
    private String activitiesJson;
}
