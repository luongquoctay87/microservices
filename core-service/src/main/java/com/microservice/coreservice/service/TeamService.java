package com.microservice.coreservice.service;

import com.microservice.coreservice.domain.form.TeamForm;
import com.microservice.coreservice.entity.Team;
import com.microservice.coreservice.entity.TeamUser;

import java.util.List;

public interface TeamService {
    Team createNewTeam(TeamForm form);

    Team updateTeam(TeamForm form, Long teamId);

    Team updateName(String name, Long teamId);

    Team findTeamById(Long id);

    List<Team> getListTeam(Long projectId);

    void deleteTeam(Long id);


}
