package com.microservice.authserver.entity;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class Result {

    private Long userId;
    private List<String> activities;
//    private Token token;
    private String activitiesJson;

}
