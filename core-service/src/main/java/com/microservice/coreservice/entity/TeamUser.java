package com.microservice.coreservice.entity;

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
@Table(name = "pa_team_users")
@NoArgsConstructor
@IdClass(TeamUser.class)
public class TeamUser implements Serializable {

    @Id
    private Long teamId;

    private Long departmentId;

    @Id
    private Long userId;

    private Timestamp createdDate;

    private Timestamp updatedDate;

    public TeamUser(Long teamId, Long departmentId, Long userId, Date createdDate, Date updatedDate) {
        this.teamId = teamId;
        this.departmentId = departmentId;
        this.userId = userId;
        this.createdDate = new Timestamp(createdDate.getTime());
        this.updatedDate = updatedDate != null ? new Timestamp(updatedDate.getTime()) : null ;
    }
}
