package com.microservice.coreservice.entity;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@Table(name = "pa_teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Boolean enabled;

    private Timestamp createdDate;

    private Timestamp updatedDate;

    public Team(Long id, String name, String description, Boolean enabled, Date createdDate, Date updatedDate) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.enabled = enabled;
        this.createdDate = new Timestamp(createdDate.getTime());
        this.updatedDate = updatedDate != null ? new Timestamp(updatedDate.getTime()) : null ;
    }
}
