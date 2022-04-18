package com.microservice.coreservice.domain.model;

import java.sql.Timestamp;

public interface ModelExcelUser {

    String getUsername();
    String getTeam();
    String getProject();
    String getDepartment();
    Long getAmountTaskDone();
}
