package com.microservice.coreservice.repository;

import com.microservice.coreservice.constants.StatusEnums;
import com.microservice.coreservice.domain.model.ModelExcelUser;
import com.microservice.coreservice.domain.model.ModelTeam;
import com.microservice.coreservice.entity.Task;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.sql.Timestamp;
import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Long> {

    @Query("SELECT t FROM Task t WHERE t.project_id = :projectId")
    List<Task> findByProjectId(Long projectId);

    @Query("SELECT t FROM Task t WHERE t.project_id = :projectId AND t.section_id = :sectionId")
    List<Task> getListTaskInSectionAndProject(Long projectId, Long sectionId);

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
    List<ModelExcelUser> getListTaskFinished(List<Long> dataInMonth);


    @Query(value = "SELECT t.id FROM pa_tasks t", nativeQuery = true)
    List<Long> findAllId();

    @Query(value = "SELECT t.created_date FROM pa_tasks t", nativeQuery = true)
    List<Timestamp> findAllCreatedDate();
    

    @Query(value = "select distinct t.name as team, p.name as project\n" +
            "from pa_teams t\n" +
            "join pa_team_users tu\n" +
            " on t.id = tu.team_id\n" +
            "join pa_departments d\n" +
            "on tu.department_id = d.id\n" +
            "join pa_projects p\n" +
            "join pa_tasks ta\n" +
            "on p.id = ta.project_id\n" +
            "where\n" +
            "p.name not in (\n" +
            "SELECT distinct(p.name) FROM pa_projects p\n" +
            "join pa_tasks ta\n" +
            "ON p.id = ta.project_id\n" +
            "Where ta.status <> 'Finished') AND ta.id in (:dataInMonth)",nativeQuery = true)
    List<ModelTeam> findProjectFinished(List<Long> dataInMonth);

    @Query(value = "SELECT distinct d.name from pa_departments d JOIN pa_team_users tu ON d.id = tu.department_id JOIN pa_teams t ON tu.team_id = t.id WHERE t.name = :s", nativeQuery = true)
    List<String> getListDepartmentByTeam(String s);
}
