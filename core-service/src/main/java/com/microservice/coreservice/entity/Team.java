package com.microservice.coreservice.entity;

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
@Table(name = "pa_teams")
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private String description;

    @Column
    private Boolean enabled;

    @Column
    private Timestamp created_date;

    @Column
    private Timestamp updated_date;

    public Team(int id, String name, String description, Boolean enabled, Date created_date, Date updated_date) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.enabled = enabled;
        this.created_date = new Timestamp(created_date.getTime());
        this.updated_date = updated_date != null ? new Timestamp(updated_date.getTime()) : null ;
    }
    public Team() {
    }
}
