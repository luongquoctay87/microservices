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
@Table(name = "pa_tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long created_by;

    @Column
    private Long assignee;

    @Column
    private Long project_id;

    @Column
    private Long section_id;

    @Column
    private String name;

    @Column
    private String description;

   @Enumerated(EnumType.STRING)
    private PriorityEnums priority;

    @Enumerated(EnumType.STRING)
    private StatusEnums status;

    @Column
    private float estimate_time;

    @Column
    private Timestamp start_date;

    @Column
    private Timestamp end_date;

    @Column(columnDefinition = "0")
    private Long parent_id;

    @Column
    private Timestamp created_date;

    @Column
    private Timestamp updated_date;


    public Task(Long id, Long created_by, Long assignee, Long project_id, Long section_id, String name, String description, PriorityEnums priority, StatusEnums status, float estimate_time, Timestamp start_date, Timestamp end_date, Long parent_id, Timestamp created_date, Timestamp updated_date) {
        this.id = id;
        this.created_by = created_by;
        this.assignee = assignee;
        this.project_id = project_id;
        this.section_id = section_id;
        this.name = name;
        this.description = description;
        this.priority = priority;
        this.status = status;
        this.estimate_time = estimate_time;
        this.start_date = start_date;
        this.end_date = end_date;
        this.parent_id = parent_id ;
        this.created_date = created_date;
        this.updated_date = updated_date;
    }

    public Task() {
    }
}
