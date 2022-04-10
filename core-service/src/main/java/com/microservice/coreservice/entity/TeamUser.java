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
@Table(name = "pa_team_users")
@IdClass(TeamUser.class)
public class TeamUser implements Serializable {

    @Id
    private int team_id;

    @Column
    private Long department_id;

    @Id
    private Long user_id;

    @Column
    private Timestamp created_date;

    @Column
    private Timestamp updated_date;

    public TeamUser(int team_id, Long department_id, Long user_id, Date created_date, Date updated_date) {
        this.team_id = team_id;
        this.department_id = department_id;
        this.user_id = user_id;
        this.created_date = new Timestamp(created_date.getTime());
        this.updated_date = updated_date != null ? new Timestamp(updated_date.getTime()) : null ;
    }

    public TeamUser() {
    }
}
