package com.microservice.coreservice.repository;

import com.microservice.coreservice.constants.StatusEnums;
import com.microservice.coreservice.domain.model.ModelExcelUser;
import com.microservice.coreservice.entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
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


    @Query(value = "select u.username as Username, te.name as Team, p.name as Project, d.name as Department,count(u.id) as amountTaskDone\n" +
            "from pa_task_management.pa_users u\n" +
            "join pa_task_management.pa_team_users tu\n" +
            "ON u.id = tu.user_id\n" +
            "join pa_task_management.pa_teams te\n" +
            "ON te.id = tu.team_id\n" +
            "join pa_task_management.pa_departments d\n" +
            "ON d.id = tu.department_id\n" +
            "join pa_task_management.pa_project_teams pt\n" +
            "ON pt.team_id = te.id\n" +
            "join pa_task_management.pa_projects p\n" +
            "ON pt.project_id = p.id\n" +
            "join pa_task_management.pa_tasks t\n" +
            "ON u.id = t.assignee\n" +
            "where t.status = 'Finished'\n" +
            "AND t.id in (:dataInMonth)\n"+
            "group by u.username\n", nativeQuery = true)
    List<ModelExcelUser> getListTaskFinished(List<Integer> dataInMonth);


    @Query(value = "SELECT t.id FROM pa_tasks t", nativeQuery = true)
    List<Integer> findAllId();

    @Query(value = "SELECT t.created_date FROM pa_tasks t", nativeQuery = true)
    List<Timestamp> findAllCreatedDate();

}
