package com.microservice.coreservice.repository;

import com.microservice.coreservice.entity.Team;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.List;

public interface TeamRepository extends CrudRepository<Team, Long> {

    boolean existsByName(String name);

    @Query("SELECT new com.microservice.coreservice.entity.Team(t.id, t.name, t.description, t.enabled, t.createdDate, t.updatedDate) FROM Team t JOIN ProjectTeam pt on t.id = pt.teamId WHERE pt.projectId = :projectId")
    List<Team> findByProjectId(Long projectId);
}
