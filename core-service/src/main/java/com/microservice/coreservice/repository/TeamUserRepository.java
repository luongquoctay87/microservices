package com.microservice.coreservice.repository;

import com.microservice.coreservice.entity.TeamUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface TeamUserRepository extends CrudRepository<TeamUser, Long> {

    @Query(value = "SELECT new com.microservice.coreservice.entity.TeamUser(t.team_id, t.department_id, t.user_id, t.created_date, t.updated_date) FROM TeamUser t WHERE t.team_id  = ?1")
    Object findByTeamId(Long teamId);

    @Modifying
    @Transactional
    @Query(value = "DELETE TeamUser p WHERE p.team_id = ?1 ")
    void deleteByTeamId(Long id);
}
