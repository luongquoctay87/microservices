package com.microservice.coreservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microservice.coreservice.constants.PriorityEnums;
import com.microservice.coreservice.constants.StatusEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

@Entity
@Data
@Builder

@Table(name = "pa_user_roles")
@IdClass(RoleUser.class)
public class RoleUser  implements Serializable {

    @Id
    private Long user_id;

    @Column(nullable = true)
    @Id
    private Long role_id;

    public RoleUser(Long user_id, Long role_id) {
        this.user_id = user_id;
        this.role_id = role_id;
    }

    public RoleUser() {
    }
}
