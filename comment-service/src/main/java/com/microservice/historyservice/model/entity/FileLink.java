package com.microservice.historyservice.model.entity;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Data
@Entity(name = "pa_file_link")
public class FileLink {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "comment_id")
    @NotNull(message = "không được để trống")
    private Long commentId;

    @Column(name = "link_file")
    @NotNull(message = "không được để trống")
    private String url;
}
