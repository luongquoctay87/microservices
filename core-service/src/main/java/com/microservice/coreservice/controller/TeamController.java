package com.microservice.coreservice.controller;

import com.microservice.coreservice.domain.dto.ProjectDto;
import com.microservice.coreservice.domain.dto.TeamDto;
import com.microservice.coreservice.domain.form.ProjectForm;
import com.microservice.coreservice.domain.form.TeamForm;
import com.microservice.coreservice.entity.Project;
import com.microservice.coreservice.entity.Team;
import com.microservice.coreservice.service.TeamService;
import com.microservice.coreservice.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teams")
@Slf4j
public class TeamController {

    @Autowired
    private TeamService teamService;

    @PostMapping()
    public ResponseEntity<ApiResponse> createNewTeam(@RequestBody TeamForm _form) {
        log.info("Team Controler -> createNewTeam");

        Team team = teamService.createNewTeam(_form);
        TeamDto data = new TeamDto(team);

        ApiResponse response = data != null ? ApiResponse.appendSuccess(data, HttpStatus.CREATED.value(), "Thêm mới nhóm thành công")
                : ApiResponse.appendError(HttpStatus.NO_CONTENT.value(), "Thêm mới nhóm thất bại");
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> findTeamById(@PathVariable(name = "id") Long _teamId) {
        log.info("Team Controler -> findTeamById");

        Team team = teamService.findTeamById(_teamId);
        TeamDto data = new TeamDto(team);
        ApiResponse response = data != null ? ApiResponse.appendSuccess(data, HttpStatus.OK.value(), null)
                : ApiResponse.appendError(HttpStatus.NO_CONTENT.value(), null);
        return ResponseEntity.ok(response);
    }

    @GetMapping()
    public ResponseEntity<ApiResponse> getTeamLists(@RequestParam(name = "projectId") Long _projectId) {
        log.info("Team Controler -> getTeamLists");

        List<Team> teams = teamService.getListTeam(_projectId);
        List<TeamDto> data = teams.stream().map(TeamDto::new).collect(Collectors.toList());
        ApiResponse  response =  ApiResponse.appendSuccess(data, HttpStatus.OK.value(), null);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> deleteTeam(@PathVariable(name = "id") Long _teamId) {
        log.info("Team Controler -> deleteTeam");

        teamService.deleteTeam(_teamId);
        ApiResponse response = ApiResponse.appendSuccess(null, HttpStatus.OK.value(), "Xóa nhóm thành công");
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateTeam(@RequestBody TeamForm _form, @PathVariable(name = "id") Long _teamId) {
        log.info("Team Controler -> updateTeam");

        Team team = teamService.updateTeam( _form ,_teamId);
        TeamDto data = new TeamDto(team);
        ApiResponse response = data != null ? ApiResponse.appendSuccess(data, HttpStatus.CREATED.value(), "Cập nhật nhóm thành công")
                : ApiResponse.appendError(HttpStatus.NO_CONTENT.value(), "Cập nhật nhóm thất bại");
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse> updateName(@RequestParam(name = "name") String _name, @PathVariable(name = "id") Long _teamId) {
        log.info("Team Controler -> updateName");

        Team team = teamService.updateName( _name ,_teamId);
        TeamDto data =  new TeamDto(team);
        ApiResponse response = data != null ? ApiResponse.appendSuccess(data, HttpStatus.CREATED.value(), "Cập nhật tên nhóm thành công")
                : ApiResponse.appendError(HttpStatus.NO_CONTENT.value(), "Cập nhật tên nhóm thất bại");
        return ResponseEntity.ok(response);
    }

}
