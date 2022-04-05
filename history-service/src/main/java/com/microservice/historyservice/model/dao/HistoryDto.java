package com.microservice.historyservice.model.dao;

import lombok.Data;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

@Data
public class HistoryDto {
    @NotNull(message = "không được để trống")
    @Positive
    private Long taskId;
    @NotNull(message = "không được để trống")
    @Positive
    private Long userId;
}
