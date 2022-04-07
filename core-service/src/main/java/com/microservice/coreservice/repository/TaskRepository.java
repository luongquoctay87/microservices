package com.microservice.coreservice.repository;

import com.microservice.coreservice.constants.StatusEnums;
import com.microservice.coreservice.entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {

    @Query("SELECT t FROM Task t WHERE t.project_id = :projectId")
    List<Task> findByProjectId(int projectId);

    @Query("SELECT t FROM Task t WHERE t.project_id = :projectId AND t.section_id = :sectionId")
    List<Task> getListTaskInSectionAndProject(int projectId, int sectionId);

    @Query(value = "SELECT t FROM Task t WHERE t.project_id IS NULL AND t.section_id IS NULL")
    List<Task> getListTaskPersonal();

    @Query("SELECT t FROM Task t WHERE t.name LIKE %?1%")
    List<Task> findByName(String textSearch);

    @Query("SELECT t FROM Task t WHERE t.status = :status")
    List<Task> findByStatus(StatusEnums status);

    @Query(value = "SELECT t.id FROM pa_tasks t", nativeQuery = true)
    List<Integer> findAllId();


}
