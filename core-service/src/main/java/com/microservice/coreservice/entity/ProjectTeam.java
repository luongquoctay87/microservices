package com.microservice.coreservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pa_project_teams")
@IdClass(ProjectTeam.class)
public class ProjectTeam implements Serializable {

    @Id
    private Long projectId;

    @Id
    private Long teamId;

    private Timestamp createdDate;

    private Timestamp updatedDate;
}
