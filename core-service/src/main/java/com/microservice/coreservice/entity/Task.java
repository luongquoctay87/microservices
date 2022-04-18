package com.microservice.coreservice.entity;
import com.microservice.coreservice.constants.PriorityEnums;
import com.microservice.coreservice.constants.StatusEnums;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.persistence.*;
import java.sql.Timestamp;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pa_tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long createdBy;

    private Long assignee;

    private Long projectId;

    private Long sectionId;

    private String name;

    private String description;

    @Enumerated(EnumType.STRING)
    private PriorityEnums priority;

    @Enumerated(EnumType.STRING)
    private StatusEnums status;

    private float estimateTime;

    private Timestamp startDate;

    private Timestamp endDate;

    private Long parentId;

    private Timestamp createdDate;

    private Timestamp updatedDate;
}
