package com.microservice.coreservice.domain.model;
import lombok.Data;

import java.util.List;

@Data
public class ResultClass {
    private Long userId;
    private List<String> activities;
    private String activiesJson;

    
}
