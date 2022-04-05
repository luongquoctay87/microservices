package com.microservice.historyservice.api.form;

import lombok.Data;

@Data
public class HistoryForm {
    private Long userId;
    private String actions;
    private String createdDate;
}
