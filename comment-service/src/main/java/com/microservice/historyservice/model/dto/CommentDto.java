package com.microservice.historyservice.model.dto;
import lombok.Data;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Positive;
import java.io.Serializable;

@Data
public class CommentDto implements Serializable {
    @NotEmpty(message = "không được để trống")
    @Positive(message = "là số nguyên dương")
    private Long userId;

    @NotEmpty(message = "không được để trống")
    @Positive(message = "là số nguyên dương")
    private Long taskId;

    @Positive(message = "là số nguyên dương")
    private Long replyId;

    @NotEmpty(message = "không được để trống")
    private String content;

    private String img;
}
