package com.microservice.coreservice.domain.form;

import com.microservice.coreservice.constants.ProjectEnums;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;

@Data
@Builder
public class ProjectForm {

    private String name;
    private String description;
    private String type;
    private Long teamId;
    private Boolean enabled;
}
