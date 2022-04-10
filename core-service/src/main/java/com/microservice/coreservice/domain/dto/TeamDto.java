package com.microservice.coreservice.domain.dto;

import com.microservice.coreservice.entity.Team;
import com.microservice.coreservice.entity.TeamUser;
import lombok.Builder;
import lombok.Data;

@Data
public class TeamDto {

    private Long id;
    private String name;
    private String description;
    private Boolean enabled;
    private Long created_date;
    private Long updated_date;

    public TeamDto(Team team) {
        this.id = team.getId();
        this.name = team.getName();
        this.description = team.getDescription();
        this.enabled = team.getEnabled();
        this.created_date = team.getCreated_date().getTime();
        this.updated_date = team.getUpdated_date() != null ? team.getUpdated_date().getTime() : null;
    }
}
