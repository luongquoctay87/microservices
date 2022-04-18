package com.microservice.coreservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pa_user_roles")
@IdClass(RoleUser.class)
public class RoleUser  implements Serializable {

    @Id
    private Long userId;

    @Column(nullable = true)
    @Id
    private Long roleId;
}
