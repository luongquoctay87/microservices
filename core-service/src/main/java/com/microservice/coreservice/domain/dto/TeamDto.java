package com.microservice.coreservice.domain.dto;

import com.microservice.coreservice.entity.Team;
import lombok.Data;
import org.apache.logging.log4j.util.Strings;

@Data
public class TeamDto {

    private Long id;
    private String name;
    private String description;
    private Boolean enabled;
    private String created_date;
    private String updated_date;

    public TeamDto(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.description = team.getDescription();
        this.enabled = team.getEnabled();
        this.created_date = String.valueOf(team.getCreatedDate());
        this.updated_date = team.getUpdatedDate() != null ? String.valueOf(team.getUpdatedDate()) : Strings.EMPTY;
    }
}
