package com.microservice.coreservice.repository;

import com.microservice.coreservice.entity.RoleUser;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import javax.transaction.Transactional;

public interface RoleUserRepository extends CrudRepository<RoleUser, Long> {


}
