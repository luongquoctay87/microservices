package com.microservice.cloudgateway.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class ErrorResponse implements Serializable {
    private Date timestamp;
    private String path;
    private int status;
    private String error;
}
