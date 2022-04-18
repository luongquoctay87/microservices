package com.microservice.coreservice.repository;

import com.microservice.coreservice.entity.TeamUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface TeamUserRepository extends CrudRepository<TeamUser, Long> {

   TeamUser findByTeamId(Long teamId);


    @Modifying
    @Transactional
    @Query(value = "DELETE TeamUser p WHERE p.teamId = ?1 ")
    void deleteByTeamId(Long id);
}
