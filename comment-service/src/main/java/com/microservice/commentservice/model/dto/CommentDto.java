package com.microservice.commentservice.model.dto;
import com.sun.istack.NotNull;
import lombok.Data;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Date;

@Data
public class CommentDto implements Serializable {
    @NotNull
    @Positive
    private Long userId;
    @NotNull
    @Positive
    private Long taskId;
    @Positive
    private Long replyId;
    @NotNull
    @Size(max = 30)
    private String content;
    private Date createdDate;
    private Date updatedDate;
    private String img;
}
