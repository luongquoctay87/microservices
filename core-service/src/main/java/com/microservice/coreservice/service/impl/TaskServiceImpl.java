package com.microservice.coreservice.service.impl;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.coreservice.constants.PriorityEnums;
import com.microservice.coreservice.constants.SortPropertyEnums;
import com.microservice.coreservice.constants.StatusEnums;
import com.microservice.coreservice.domain.dto.TaskSearchReponse;
import com.microservice.coreservice.domain.form.TaskForm;
import com.microservice.coreservice.domain.form.TaskSearchForm;
import com.microservice.coreservice.domain.model.ResultClass;
import com.microservice.coreservice.entity.Project;
import com.microservice.coreservice.entity.Section;
import com.microservice.coreservice.entity.Task;
import com.microservice.coreservice.repository.ProjectRepository;
import com.microservice.coreservice.repository.RedisRepository;
import com.microservice.coreservice.repository.SectionRepository;
import com.microservice.coreservice.repository.TaskRepository;
import com.microservice.coreservice.service.TaskService;
import com.microservice.coreservice.utils.ValidateUtils;
import com.microservice.coreservice.utils.pagination.PageResponse;
import com.microservice.coreservice.utils.pagination.Pages;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.WeekFields;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private RedisRepository redisRepository;

    @Autowired
    private EntityManager entityManager;

    @Override
    public PageResponse<TaskSearchReponse> search(int page,
                                                  int pageSize,
                                                  TaskSearchForm request) {
        log.info("TaskService -> search");

        List<TaskSearchReponse> taskSearchResponseList = new ArrayList<>();

        String nativeQuery = "SELECT" +
                "t.id," +
                "t.name as taskName," +
                "t.created_by," +
                "t.project_id," +
                "t.section_id," +
                "t.description,\n" +
                "t.priority," +
                "t.status," +
                "t.estimate_time," +
                "t.start_date," +
                "t.end_date," +
                "t.parent_id," +
                "t.created_date," +
                "t.updated_date," +
                "p.name as projectName," +
                "s.name as sectionName," +
                "u.username\n" +
                "FROM pa_tasks t " +
                "JOIN pa_projects p ON t.project_id = p.id\n" +
                "JOIN pa_sections s ON t.section_id = s.id\n" +
                "JOIN pa_users u ON t.assignee = u.id\n" +
                "WHERE p.id = (?1) AND s.id = (?2)";

        Query query = entityManager.createNativeQuery(nativeQuery)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .setParameter(1, request.getProject_id())
                .setParameter(2, request.getSection_id());

        // Trường hợp request gửi lên không null textsearch
        if (request.getTextSearch() != null && request.getTextSearch().trim().length() > 0) {

            nativeQuery += "AND t.name LIKE CONCAT('%','" + request.getTextSearch() + "', '%')";

            // Trường hợp người dùng tìm kiếm kèm lọc kết quả theo điều kiện
            if (request.getSortProperty() == null && request.getSortOrder() == null && request.getFilterBy() != null) {
                searchByTextSearchAndFilter(nativeQuery, request, query);
            }

            // Trường hợp người dùng tìm kiếm kèm sắp xếp theo điều kiện
            if (request.getSortProperty() != null && request.getSortOrder() != null && request.getFilterBy() == null) {
                searchByTextSearchAndSort(nativeQuery, request);
            }

            //   Trường hợp người dùng tìm kiếm kèm sắp xếp và lọc kết quả theo điều kiện
            if (request.getSortProperty() != null && request.getSortOrder() != null && request.getFilterBy() != null) {
                searchByTextSearchAndFilter(nativeQuery, request, query);
                searchByTextSearchAndSort(nativeQuery, request);
            }

            // Trường hợp request gửi lên  null textsearch
        } else {

//           Trường hợp người dùng lọc kết quả theo điều kiện
            if (request.getSortProperty() == null && request.getSortOrder() == null && request.getFilterBy() != null) {
                searchByTextSearchAndFilter(nativeQuery, request, query);
            }

//             Trường hợp người dùng sắp xếp theo điều kiện
            if (request.getSortProperty() != null && request.getSortOrder() != null && request.getFilterBy() == null) {
                searchByTextSearchAndSort(nativeQuery, request);
            }

//             Trường hợp người dùng săp xếp và lọc kết quả theo điều kiện
            if (request.getSortProperty() != null && request.getSortOrder() != null && request.getFilterBy() != null) {
                searchByTextSearchAndFilter(nativeQuery, request, query);
                searchByTextSearchAndSort(nativeQuery, request);
            }
        }

        List<Object[]> objects = query.getResultList();

        int totalElements = objects.size();

        if (!objects.isEmpty()) {
            for (Object[] object : objects) {
                int taskId = (int) object[0];
                String taskName = Objects.isNull(object[1]) ? null : object[1].toString();
                int create_by = (int) object[2];
                Integer project_id = (int) object[3];
                Integer section_id = (int) object[4];
                String description = Objects.isNull(object[5]) ? null : object[5].toString();
                String priority = Objects.isNull(object[6]) ? null : object[6].toString();
                String status = Objects.isNull(object[7]) ? null : object[7].toString();
                float estimate_time = (float) object[8];
                Timestamp start_day = Objects.isNull(object[9]) ? null : (Timestamp) object[9];
                Timestamp end_day = Objects.isNull(object[10]) ? null : (Timestamp) object[10];
                int parent_id = (int) object[11];
                Timestamp created_day = Objects.isNull(object[12]) ? null : (Timestamp) object[12];
                Timestamp updated_day = Objects.isNull(object[13]) ? null : (Timestamp) object[13];
                String project_name = Objects.isNull(object[14]) ? null : object[14].toString();
                String section_name = Objects.isNull(object[15]) ? null : object[15].toString();
                String assigneeName = Objects.isNull(object[16]) ? null : object[16].toString();

                TaskSearchReponse taskSearchReponse = TaskSearchReponse.builder()
                        .id(taskId)
                        .name(taskName)
                        .assigneeName(assigneeName)
                        .startDay(start_day.getTime())
                        .endDay(end_day.getTime())
                        .priority(priority)
                        .jobDescription(description)
                        .status(status)
                        .parentId(parent_id)
                        .projectId(project_id != null ? project_id : null)
                        .sectionId(section_id != null ? section_id : null)
                        .projectName(project_name)
                        .sectionName(section_name)
                        .created_by(create_by)
                        .estimate_time(estimate_time)
                        .created_date(created_day.getTime())
                        .updated_date(updated_day != null ? updated_day.getTime() : null)
                        .build();

                taskSearchResponseList.add(taskSearchReponse);
            }

            if (request.getDataType() != null) {

                if (Objects.equals("today", request.getDataType()) && !CollectionUtils.isEmpty(taskSearchResponseList)) {

                    List<TaskSearchReponse> responses = getTaskSearchResponseByWeekNow(taskSearchResponseList);
                    int totalElement = responses.size();
                    Pages pages = getPages(totalElement, pageSize, page);
                    boolean hasNext = page < pages.getTotalPages();
                    boolean hasPrevious = page > 1;
                    return new PageResponse<>(
                            responses,
                            pages.getTotalPages(),
                            pages.getTotalPages(),
                            hasNext,
                            hasPrevious
                    );
                }

                if (Objects.equals("week", request.getDataType()) && !CollectionUtils.isEmpty(taskSearchResponseList)) {

                    List<TaskSearchReponse> responses = getTaskSearchResponseByWeekNow(taskSearchResponseList);
                    int totalElement = responses.size();
                    Pages pages = getPages(totalElement, pageSize, page);
                    boolean hasNext = page < pages.getTotalPages();
                    boolean hasPrevious = page > 1;
                    return new PageResponse<>(
                            responses,
                            pages.getTotalPages(),
                            pages.getTotalPages(),
                            hasNext,
                            hasPrevious
                    );
                }

                if (Objects.equals("month", request.getDataType()) && !CollectionUtils.isEmpty(taskSearchResponseList)) {

                    List<TaskSearchReponse> responses = getTaskSearchResponseByMonthNow(taskSearchResponseList);
                    int totalElement = responses.size();
                    Pages pages = getPages(totalElement, pageSize, page);
                    boolean hasNext = page < pages.getTotalPages();
                    boolean hasPrevious = page > 1;
                    return new PageResponse<>(
                            responses,
                            pages.getTotalPages(),
                            pages.getTotalPages(),
                            hasNext,
                            hasPrevious
                    );
                }
            }
            Pages pages = getPages(totalElements, pageSize, page);
            boolean hasNext = page < pages.getTotalPages();
            boolean hasPrevious = page > 1;

            return new PageResponse<>(
                    taskSearchResponseList,
                    pages.getTotalPages(),
                    pages.getTotalPages(),
                    hasNext,
                    hasPrevious
            );
        }

        List<TaskSearchReponse> taskSearchResponseIsEmpty = new ArrayList<>();
        return new PageResponse<>(taskSearchResponseIsEmpty);
    }

    private void searchByTextSearchAndSort(String nativeQuery, TaskSearchForm request) {
        if (SortPropertyEnums.valueOf(request.getSortProperty()) == SortPropertyEnums.ByEndDay) {
            if (request.getSortOrder().equals("ASC")) {
                nativeQuery += "ORDER BY t.end_date ASC";
            } else {
                nativeQuery += "ORDER BY t.end_date DESC";
            }
        } else if (SortPropertyEnums.valueOf(request.getSortProperty()) == SortPropertyEnums.ByCreatedDay) {
            if (request.getSortOrder().equals("ASC")) {
                nativeQuery += "ORDER BY t.created_date ASC";
            } else {
                nativeQuery += "ORDER BY t.created_date DESC";
            }
        } else if (SortPropertyEnums.valueOf(request.getSortProperty()) == SortPropertyEnums.ByAssignee) {
            if (request.getSortOrder().equals("ASC")) {
                nativeQuery += "ORDER BY t.assignee ASC";
            } else {
                nativeQuery += "ORDER BY t.assignee DESC";
            }
        } else if (SortPropertyEnums.valueOf(request.getSortProperty()) == SortPropertyEnums.ByPriority) {
            if (request.getSortOrder().equals("ASC")) {
                nativeQuery += "ORDER BY t.priority ASC";
            } else {
                nativeQuery += "ORDER BY t.priority DESC";
            }
        }
    }

    private void searchByTextSearchAndFilter(String nativeQuery, TaskSearchForm request, Query query) {


        if (Objects.equals(request.getFilterBy(), "Finished")) {
            query.setParameter(3, "Finished");
            nativeQuery += "WHERE t.status = (?3)";
        } else if (Objects.equals(request.getFilterBy(), "InProgress")) {
            query.setParameter(3, "InProgress");
            nativeQuery += "WHERE t.status = (?3)";
        }
    }

    private Pages getPages(int totalElements, int pageSize, int page) {
        int totalPages = 0;
        if (totalElements > 0) {
            totalPages = (int) (totalElements % pageSize == 0 ?
                    totalElements / pageSize :
                    totalElements / pageSize + 1);
        }
        Pages pages = new Pages(totalElements, pageSize, page, totalPages);
        return pages;
    }

    private List<TaskSearchReponse> getTaskSearchResponseByToday(List<TaskSearchReponse> taskSearchResponseList) {
        if (taskSearchResponseList != null) {
            List<Long> taskCreatedDay = taskSearchResponseList.stream().map(TaskSearchReponse::getCreated_date).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(taskCreatedDay)) {
                LocalDate today = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int day = today.getDayOfMonth();
                List<TaskSearchReponse> taskSearchReponsesList = new ArrayList<>();

                for (int i = 0; i < taskCreatedDay.size(); i++) {
                    Date date = new Date(taskCreatedDay.get(i));
                    LocalDate localDateOfT = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int dayOfMonth = localDateOfT.getDayOfMonth();
                    if (dayOfMonth == day) {
                        taskSearchReponsesList.add(taskSearchResponseList.get(i));
                    }
                }
                if (!CollectionUtils.isEmpty(taskSearchReponsesList)) {
                    return taskSearchReponsesList;
                }
            }
        }
        return new ArrayList<>();
    }

    private List<TaskSearchReponse> getTaskSearchResponseByWeekNow(List<TaskSearchReponse> taskSearchResponseList) {
        if (taskSearchResponseList != null) {
            List<Long> taskCreatedDay = taskSearchResponseList.stream().map(TaskSearchReponse::getCreated_date).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(taskCreatedDay)) {
                LocalDate dateNow = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int weekOfYearNow = dateNow.get(WeekFields.of(DayOfWeek.MONDAY, 7).weekOfYear());
                List<TaskSearchReponse> taskSearchReponsesList = new ArrayList<>();

                for (int i = 0; i < taskCreatedDay.size(); i++) {
                    Date date = new Date(taskCreatedDay.get(i));
                    LocalDate localDateOfT = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int weekOfYear = localDateOfT.get(WeekFields.of(DayOfWeek.MONDAY, 7).weekOfYear());
                    if (weekOfYear == weekOfYearNow) {
                        taskSearchReponsesList.add(taskSearchResponseList.get(i));
                    }
                }
                if (!taskSearchReponsesList.isEmpty()) {
                    return taskSearchReponsesList;
                }
            }
        }
        return new ArrayList<>();
    }

    private List<TaskSearchReponse> getTaskSearchResponseByMonthNow(List<TaskSearchReponse> taskSearchResponseList) {
        if (taskSearchResponseList != null) {
            List<Long> taskCreatedDay = taskSearchResponseList.stream().map(TaskSearchReponse::getCreated_date).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(taskCreatedDay)) {
                LocalDate dateNow = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int monthNow = dateNow.getMonthValue();
                List<TaskSearchReponse> taskSearchResponsesList = new ArrayList<>();
                for (int i = 0; i < taskCreatedDay.size(); i++) {
                    Date date = new Date(taskCreatedDay.get(i));
                    LocalDate localDateOfT = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int month = localDateOfT.getMonthValue();
                    if (month == monthNow) {
                        taskSearchResponsesList.add(taskSearchResponseList.get(i));
                    }
                }
                if (!taskSearchResponsesList.isEmpty()) {
                    return taskSearchResponsesList;
                }
            }
        }
        return new ArrayList<>();
    }

    @Override
    public Task addNewTask(TaskForm taskForm, String token) {
        log.info("TaskService -> addNewTask");

        String token1 = token.substring(7);
        String redisResult = redisRepository.findResdisData(token1);
        ObjectMapper mapper = new ObjectMapper();

        ResultClass resultClass = null;
        try {
            resultClass = mapper.readValue(redisResult, ResultClass.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonGenerationException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Long id = resultClass.getUserId();
        int userId = id.intValue();
        validateTaskForm(taskForm);

        Task task = Task.builder()
                .name(taskForm.getName())
                .assignee(taskForm.getAssignee())
                .created_by(userId)
                .description(taskForm.getJobDescription())
                .priority(PriorityEnums.valueOf(taskForm.getPriority()))
                .status(StatusEnums.valueOf(taskForm.getStatus()))
                .estimate_time(taskForm.getEstimate_time())
                .start_date(new Timestamp(taskForm.getStartDay()))
                .end_date(new Timestamp(taskForm.getEndDay()))
                .created_date(new Timestamp(System.currentTimeMillis()))
                .build();
        taskRepository.save(task);

        if (taskForm.getSectionId() != null) {
            Section section = sectionRepository.findById(taskForm.getSectionId()).get();
            if (!ObjectUtils.isEmpty(section)) {
                task.setSection_id(taskForm.getSectionId());
            }
        }

        if (taskForm.getProjectId() != null) {
            Project project = projectRepository.findById(taskForm.getProjectId()).get();
            if (!ObjectUtils.isEmpty(project)) {
                task.setProject_id(taskForm.getProjectId());
            }
        }

        if (taskForm.getParentId() != 0) {
            Task parent = taskRepository.findById(taskForm.getParentId()).get();
            if (!ObjectUtils.isEmpty(parent)) {
                task.setParent_id(taskForm.getParentId());
            }
        }

        taskRepository.save(task);
        return task;
    }

    @Override
    public Task getById(int taskId) {
        log.info("TaskService -> getById");

        Task task = taskRepository.findById(taskId).get();

        if (ObjectUtils.isEmpty(task)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy công việc");
        } else {
            return task;
        }
    }

    @Override
    public Task updateTask(TaskForm taskForm, int taskId) {
        log.info("TaskService -> updateTask");

        validateUpdateTask(taskForm, taskId);

        Task task = taskRepository.findById(taskId).get();

        if (task != null) {
            task.setName(taskForm.getName());
            task.setDescription(taskForm.getJobDescription());
            task.setAssignee(taskForm.getAssignee());
            task.setUpdated_date(new Timestamp(System.currentTimeMillis()));
            task.setStart_date(new Timestamp(taskForm.getStartDay()));
            task.setEnd_date(new Timestamp(taskForm.getEndDay()));
            task.setPriority(PriorityEnums.valueOf(taskForm.getPriority()));
            task.setStatus(StatusEnums.valueOf(taskForm.getStatus()));
            task.setEstimate_time((taskForm.getEstimate_time()));
            taskRepository.save(task);
        }
        return task;
    }

    @Override
    public Task updateStatus(int id, String status) {
        log.info("TaskService -> updateStatus");

        Task task = taskRepository.findById(id).get();
        if (ObjectUtils.isEmpty(task)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy công việc");
        }
        task.setStatus(StatusEnums.valueOf(status));
        task.setUpdated_date(new Timestamp(System.currentTimeMillis()));
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getListTask(Integer projectId, Integer sectionId) {
        log.info("TaskService -> getListTask");

        Project project = null;
        Section section = null;

        if (projectId != null) {
            if (!projectRepository.existsById(projectId)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy dự án");
            }
            project = projectRepository.findById(projectId).get();

        }
        if (sectionId != 0) {
            if (!sectionRepository.existsById(sectionId)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy giai đoạn");
            }
            section = sectionRepository.findById(sectionId).get();

        }

        List<Task> tasks = new ArrayList<>();

        if (!ObjectUtils.isEmpty(project) && !ObjectUtils.isEmpty(section)) {
            tasks = taskRepository.getListTaskInSectionAndProject(projectId, sectionId);

        } else if (ObjectUtils.isEmpty(section) && !ObjectUtils.isEmpty(project)) {
            tasks = taskRepository.findByProjectId(projectId);

        } else if (ObjectUtils.isEmpty(project) && ObjectUtils.isEmpty(section)) {
            tasks = taskRepository.getListTaskPersonal();
        }

        if (CollectionUtils.isEmpty(tasks)) {
            return Collections.EMPTY_LIST;
        }
        return tasks;
    }

    @Override
    public void deleteTask(int taskId) {
        log.info("TaskService -> deleteTask");

        Task task = taskRepository.findById(taskId).get();
        if (ObjectUtils.isEmpty(task)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy công việc");
        }

        taskRepository.deleteById(taskId);
    }

    private void validateTaskForm(TaskForm taskForm) {
        HashMap<String, String> map = new HashMap<>();
        map.put("Tên dự án", taskForm.getName());
        map.put("Mô tả dự án", taskForm.getJobDescription());
        ValidateUtils.validateNullOrBlankString(map);

        if (taskForm.getProjectId() != null) {
            if (!projectRepository.existsById(taskForm.getProjectId())) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy dự án");
            }
        }

    }

    private void validateUpdateTask(TaskForm taskForm, int taskId) {

        HashMap<String, String> map = new HashMap<>();
        map.put("Tên dự án", taskForm.getName());
        map.put("Mô tả dự án", taskForm.getJobDescription());
        ValidateUtils.validateNullOrBlankString(map);

        if (!taskRepository.existsById(taskId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy công việc");
        }
    }
}