package com.microservice.coreservice.domain.dto;

import com.microservice.coreservice.entity.Project;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectDto {

    private String name;
    private String description;
    private String type;
    private boolean enabled;
    private Long created_day;
    private Long updated_day;

    public static ProjectDto toDto(Project project) {
        return ProjectDto.builder()
                .name(project.getName())
                .description(project.getDescription())
                .type(project.getType().name())
                .enabled(project.getEnabled())
                .created_day(project.getCreated_date().getTime())
                .updated_day(project.getUpdated_date() != null ? project.getUpdated_date().getTime() : null)
                .build();

    }

}
