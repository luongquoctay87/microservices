package com.microservice.coreservice.domain.dto;

import com.microservice.coreservice.entity.Project;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ProjectDto {

    private Long id;
    private String name;
    private String description;
    private String type;
    private boolean enabled;
    private Long created_day;
    private Long updated_day;

    public static ProjectDto toDto(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName() != null ? project.getName() : null)
                .description(project.getDescription() != null ? project.getDescription() :  null)
                .type(project.getType() != null ? project.getType().name() : null)
                .enabled(project.getEnabled())
                .created_day(project.getCreated_date() != null ? project.getCreated_date().getTime() : null)
                .updated_day(project.getUpdated_date() != null ? project.getUpdated_date().getTime() : null)
                .build();

    }

}
