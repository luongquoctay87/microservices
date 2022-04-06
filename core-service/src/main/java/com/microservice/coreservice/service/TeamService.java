package com.microservice.coreservice.service;

import com.microservice.coreservice.domain.form.TeamForm;
import com.microservice.coreservice.entity.Team;
import com.microservice.coreservice.entity.TeamUser;

import java.util.List;

public interface TeamService {
    Team createNewTeam(TeamForm form);

    Team updateTeam(TeamForm form, int teamId);

    Team updateName(String name, int teamId);

    Team findTeamById(int id);

    List<Team> getListTeam(int projectId);

    void deleteTeam(int id);
}
