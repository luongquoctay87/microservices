package com.microservice.coreservice.repository;

import com.microservice.coreservice.entity.Section;
import org.springframework.data.repository.CrudRepository;

public interface SectionRepository extends CrudRepository<Section, Long> {
}
