package com.microservice.coreservice.repository;

import com.microservice.coreservice.entity.ProjectTeam;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface ProjectTeamRepository extends CrudRepository<ProjectTeam, Long> {
    @Query(value = "SELECT new com.microservice.coreservice.entity.ProjectTeam (p.project_id, p.team_id, p.created_date, p.updated_date)  FROM ProjectTeam p WHERE p.team_id = ?1")
    Object findByTeamId(Long teamId);

    @Transactional
    @Modifying
    @Query(value = "DELETE ProjectTeam p WHERE p.team_id = :id")
    void deleteByTeamId(Long id);

}
