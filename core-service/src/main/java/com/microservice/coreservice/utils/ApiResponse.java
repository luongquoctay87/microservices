package com.microservice.coreservice.utils;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.Collections;

@Data
@Builder
@AllArgsConstructor
public class ApiResponse {

    private String status;
    private int code;
    private String message;
    private Object data;

    public static ApiResponse appendSuccess(Object data, int code, String message) {
        Object dataTmp = new ArrayList<>();
        if (!ObjectUtils.isEmpty(data)) {
            dataTmp = data;
        }
        return ApiResponse.builder()
                .status("Success")
                .message(StringUtils.hasText(message) ? message : "")
                .code(code)
                .data(dataTmp)
                .build();
    }

    public static ApiResponse appendError(int code, String message) {

        return ApiResponse.builder()
                .status("UnSuccess")
                .message(StringUtils.hasText(message) ? message : "")
                .code(code)
                .data(Collections.EMPTY_LIST)
                .build();
    }
}