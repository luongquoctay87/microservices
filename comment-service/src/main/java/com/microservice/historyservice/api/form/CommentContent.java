package com.microservice.historyservice.api.form;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CommentContent {
   @NotNull
    private String content;
}