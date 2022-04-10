package com.microservice.coreservice.entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Builder
@Table(name = "pa_project_teams")
@IdClass(ProjectTeam.class)
public class ProjectTeam implements Serializable {

    @Id
    private Long project_id;

    @Id
    private Long team_id;

    @Column
    private Timestamp created_date;

    @Column
    private Timestamp updated_date;

    public ProjectTeam(Long project_id, Long team_id, Date created_date, Date updated_date) {
        this.project_id = project_id;
        this.team_id = team_id;
        this.created_date = new Timestamp(created_date.getTime());
        this.updated_date = updated_date != null ? new Timestamp(updated_date.getTime()) : null ;
    }

    public ProjectTeam() {
    }
}
