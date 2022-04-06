package com.microservice.coreservice.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.microservice.coreservice.constants.PriorityEnums;
import com.microservice.coreservice.constants.StatusEnums;
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
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "pa_roles")
public class Role {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column
    private String name;

    @Column
    private Boolean enabled;

    @Column
    private Timestamp created_date;

    @Column
    private Timestamp updated_date;



}
