package com.microservice.coreservice.repository;

import com.microservice.coreservice.entity.Role;
import org.springframework.data.repository.CrudRepository;

public interface RoleRepository extends CrudRepository<Role, Integer> {
}
