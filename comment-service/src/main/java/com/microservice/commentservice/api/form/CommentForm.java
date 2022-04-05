package com.microservice.commentservice.api.form;
import lombok.Data;
@Data
public class CommentForm {
    private Long id;
    private String content;
    private String createdDate;
    private String updatedDate;
    private Long userId;
    private String img;
}
