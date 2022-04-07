package com.microservice.coreservice.service.impl;

import com.microservice.coreservice.domain.form.TeamForm;
import com.microservice.coreservice.entity.Department;
import com.microservice.coreservice.entity.ProjectTeam;
import com.microservice.coreservice.entity.Team;
import com.microservice.coreservice.entity.TeamUser;
import com.microservice.coreservice.repository.ProjectTeamRepository;
import com.microservice.coreservice.repository.TeamRepository;
import com.microservice.coreservice.repository.TeamUserRepository;
import com.microservice.coreservice.service.TeamService;
import com.microservice.coreservice.utils.ValidateUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Timestamp;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

@Service
@Slf4j
public class TeamServiceImpl implements TeamService {

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamUserRepository teamUserRepository;

    @Autowired
    private ProjectTeamRepository projectTeamRepository;

    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Team createNewTeam(TeamForm form) {
        log.info("TeamService -> createNewTeam");

        validateForm(form);
        Team team = Team.builder()
                .name(form.getName())
                .description(form.getDescription())
                .enabled(form.getEnabled())
                .created_date(new Timestamp(System.currentTimeMillis()))
                .build();
        teamRepository.save(team);

        if(!CollectionUtils.isEmpty(form.getProjectIds()) && form.getProjectIds().size() > 0) {

            form.getProjectIds().forEach(id -> {
                ProjectTeam projectTeam = ProjectTeam.builder()
                        .project_id(id)
                        .team_id(team.getId())
                        .created_date(new Timestamp(System.currentTimeMillis()))
                        .build();
                projectTeamRepository.save(projectTeam);
            });
        }

        if(form.getUserIds().size() > 0 && form.getDepartmentIds().size() > 0) {

            HashMap<Integer, Integer> map = new HashMap<>();

            form.getDepartmentIds().forEach(id -> {
                Integer keyMap = 0;
                map.put(keyMap, id);
                keyMap++;
            });

            form.getUserIds().forEach(id -> {
                Integer idx = 0;
                TeamUser teamUser = TeamUser.builder()
                        .user_id(id)
                        .team_id(team.getId())
                        .department_id(map.get(idx))
                        .created_date(new Timestamp(System.currentTimeMillis()))
                        .build();
                teamUserRepository.save(teamUser);
                idx++;
            });
        }
        return team;
    }

    @Override
    public Team updateTeam(TeamForm form, int teamId) {
        log.info("TeamService -> updateTeam");

//        List<Long> department = (List<Long>) restTemplate.getForObject("http://DEPARTMENT-SERVICE/departments" , Department.class);
//
//        form.getDepartmentIds().forEach(id -> {
//            if(department.)
//        });

        validateUpdateForm(form, teamId);
        Team team = teamRepository.findById(teamId).get();
        team.setName(form.getName());
        team.setDescription(form.getDescription());
        team.setEnabled(form.getEnabled());
        team.setUpdated_date(new Timestamp(System.currentTimeMillis()));
        teamRepository.save(team);
        if(!CollectionUtils.isEmpty(form.getProjectIds()) && form.getProjectIds().size() > 0) {

            ProjectTeam projectTeam = (ProjectTeam) projectTeamRepository.findByTeamId(teamId);

            if(projectTeam != null) {
                form.getProjectIds().forEach(id -> {

                    if(projectTeam.getTeam_id() != id) {

                        projectTeam.setProject_id(id);
                        projectTeam.setUpdated_date(new Timestamp(System.currentTimeMillis()));
                        projectTeamRepository.save(projectTeam);
                    }
                });
            }
        }

        if(form.getUserIds().size() > 0 && form.getDepartmentIds().size() > 0) {

            HashMap<Integer, Integer> map = new HashMap<>();

            form.getDepartmentIds().forEach(id -> {
                Integer keyMap = 0;
                map.put(keyMap, id);
                keyMap++;
            });

            TeamUser teamUser = (TeamUser) teamUserRepository.findByTeamId(teamId);

            if(teamUser != null) {
                form.getUserIds().forEach(id -> {
                    Integer idx = 0;

                    teamUser.setUser_id(id);
                    teamUser.setDepartment_id(map.get(idx));
                    teamUser.setUpdated_date(new Timestamp(System.currentTimeMillis()));
                    teamUserRepository.save(teamUser);
                    idx++;
                });
            }
        }
        return team;
    }

    @Override
    public Team updateName(String name, int teamId) {
        log.info("TeamService -> updateName");

        ValidateUtils.validateNullOrBlankString(name);
        if(!teamRepository.existsById(teamId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nhóm không tồn tại");
        }

        Team team = teamRepository.findById(teamId).get();
        team.setName(name);
        team.setUpdated_date(new Timestamp(System.currentTimeMillis()));

        return teamRepository.save(team);
    }

    @Override
    public Team findTeamById(int id) {
        log.info("TeamService -> findTeamById");

        Team team = teamRepository.findById(id).get();
        if(ObjectUtils.isEmpty(team)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy team");
        }
        return team;
    }

    @Override
    public List<Team> getListTeam(int projectId) {
        log.info("TeamService -> getListTeam");

        try {
            List<Team> teams =  teamRepository.findByProjectId(projectId);
            return teams;
        }catch (NullPointerException e) {
            return Collections.emptyList();        }
    }

    @Override
    public void deleteTeam(int id) {
        log.info("TeamService -> deleteTeam");

        if(!teamRepository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nhóm không tồn tại");
        }

        projectTeamRepository.deleteByTeamId(id);
        teamUserRepository.deleteByTeamId(id);

        teamRepository.deleteById(id);
    }

    private void validateForm(TeamForm form) {

        HashMap<String, String> map = new HashMap<>();
        map.put("Tên", form.getName());
        map.put("Mô tả", form.getDescription());
        ValidateUtils.validateNullOrBlankString(map);
    }

    private void validateUpdateForm(TeamForm form, int teamId) {

        HashMap<String, String> map = new HashMap<>();
        map.put("Tên", form.getName());
        map.put("Mô tả", form.getDescription());
        ValidateUtils.validateNullOrBlankString(map);

        if(!teamRepository.existsById(teamId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nhóm không tồn tại");
        }
    }
}
