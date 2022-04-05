package com.microservice.historyservice.model.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

@Data
@Entity(name = "pa_histories")
@AllArgsConstructor
@NoArgsConstructor
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "task_id")
    private Long taskId;
    @Column(name = "user_id")
    private Long userId;
    @Column(name = "actions")
    private String actions;
    @Column(name = "created_date")
    private Date createdDate;
}
