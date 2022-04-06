package com.microservice.coreservice.repository;

import com.microservice.coreservice.entity.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Integer> {
    boolean existsByName(String name);

    @Query("SELECT new com.microservice.coreservice.entity.Team(t.id, t.name, t.description, t.enabled, t.created_date, t.updated_date) FROM Team t JOIN ProjectTeam pt on t.id = pt.team_id WHERE pt.project_id = :projectId")
    List<Team> findByProjectId(int projectId);


}
