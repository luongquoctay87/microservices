package com.microservice.coreservice.repository;

import com.microservice.coreservice.entity.ProjectTeam;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface ProjectTeamRepository extends CrudRepository<ProjectTeam, Integer> {


}
