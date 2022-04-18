package com.microservice.coreservice.repository;

import com.microservice.coreservice.entity.ProjectTeam;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface ProjectTeamRepository extends CrudRepository<ProjectTeam, Long> {

    ProjectTeam findByTeamId(Long teamId);

    @Transactional
    @Modifying
    @Query(value = "DELETE ProjectTeam p WHERE p.teamId = :id")
    void deleteByTeamId(Long id);
}
