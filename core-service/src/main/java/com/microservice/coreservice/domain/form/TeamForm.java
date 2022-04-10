package com.microservice.coreservice.domain.form;


import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class TeamForm {


    private String name;
    private String description;
    private Boolean enabled;
    private List<Long> departmentIds;
   private List<Long> projectIds;
    private List<Long> userIds;

}
