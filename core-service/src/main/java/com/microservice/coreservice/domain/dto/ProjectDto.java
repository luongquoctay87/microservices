package com.microservice.coreservice.domain.dto;

import com.microservice.coreservice.entity.Project;
import lombok.Builder;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

@Data
@Builder
public class ProjectDto {

    private Long id;
    private String name;
    private String description;
    private String type;
    private Boolean enabled;
    private String createdDate;
    private String updatedDate;

    public static ProjectDto toDto(Project project) {
        return ProjectDto.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .type(project.getType().name())
                .enabled(project.getEnabled())
                .createdDate(project.getCreatedDate() != null ? String.valueOf(project.getCreatedDate()) : Strings.EMPTY)
                .updatedDate(project.getUpdatedDate() != null ? String.valueOf(project.getUpdatedDate()) : Strings.EMPTY)
                .build();
    }
}
