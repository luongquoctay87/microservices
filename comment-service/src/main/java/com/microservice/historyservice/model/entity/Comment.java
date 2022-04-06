package com.microservice.historyservice.model.entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Entity(name="pa_comments")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name="user_id")
    private Long userId;
    @Column(name = "task_id")
    private Long taskId;
    @Column(name = "reply_id")
    private Long replyId;
    @Column(name = "content")
    private String content;
    @Column(name = "enabled")
    private Boolean enabled;
    @Column(name = "created_date")
    private Date createdDate;
    @Column(name = "updated_date")
    private Date updatedDate;

}
