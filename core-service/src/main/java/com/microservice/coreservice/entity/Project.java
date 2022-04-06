package com.microservice.coreservice.entity;

import com.microservice.coreservice.constants.ProjectEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Builder
@Table(name = "pa_projects")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String description;

    @Enumerated(EnumType.STRING)
    private ProjectEnums type;

    @Column
    private Boolean enabled;

    @Column
    private Timestamp created_date;

    @Column
    private Timestamp updated_date;

    public Project(int id, String name, String description, ProjectEnums type, Boolean enabled, Date created_date, Date updated_date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.type = type;
        this.enabled = enabled;
        this.created_date = new Timestamp(created_date.getTime());
        this.updated_date = updated_date != null ? new Timestamp(updated_date.getTime()) : null ;
    }

    public Project() {
    }
}
