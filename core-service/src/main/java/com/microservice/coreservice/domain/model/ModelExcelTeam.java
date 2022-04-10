package com.microservice.coreservice.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ModelExcelTeam {

    private String team;
    private String department;
    private Integer amountProjectDone;
}
