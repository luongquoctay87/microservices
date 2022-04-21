package com.microservice.coreservice.service.impl;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.coreservice.constants.PriorityEnums;
import com.microservice.coreservice.constants.SortPropertyEnums;
import com.microservice.coreservice.constants.StatusEnums;
import com.microservice.coreservice.domain.dto.TaskSearchReponse;
import com.microservice.coreservice.domain.form.ColumExcel;
import com.microservice.coreservice.domain.form.TaskForm;
import com.microservice.coreservice.domain.form.TaskSearchForm;
import com.microservice.coreservice.domain.model.ModelExcelTeam;
import com.microservice.coreservice.domain.model.ModelExcelUser;
import com.microservice.coreservice.domain.model.ModelTeam;
import com.microservice.coreservice.domain.model.ResultClass;
import com.microservice.coreservice.entity.Project;
import com.microservice.coreservice.entity.Section;
import com.microservice.coreservice.entity.Task;
import com.microservice.coreservice.helper.ExcelHelper;
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
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
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

        List<Long> taskIds = new ArrayList<>();
        List<Task> tasks = new ArrayList<>();
        List<Long> taskList = taskRepository.findAllId();

        //        Trường hợp người dùng search
        if (request.getTextSearch() != null && request.getFilterBy() == null) {
            tasks = taskRepository.findByName(request.getTextSearch());

            //        Trường hợp người dùng filter
        } else if (request.getTextSearch() == null && request.getFilterBy() != null) {
            StatusEnums status = StatusEnums.valueOf(request.getFilterBy());
            tasks = taskRepository.findByStatus(status);
        }

        tasks.forEach(t -> {
            taskList.forEach(id -> {
                if (t.getId() == id) {
                    taskIds.add(t.getId());
                }
            });
        });

        String nativeQuery = "SELECT " +
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
                "FROM pa_tasks t JOIN pa_projects p ON t.project_id = p.id\n" +
                "JOIN pa_sections s ON t.section_id = s.id\n" +
                "JOIN pa_users u ON t.assignee = u.id\n" +
                "WHERE t.id in :taskId OR t.id in :status AND p.id = :projectId AND s.id = :sectionId";

        Query query = entityManager.createNativeQuery(nativeQuery)
                .setFirstResult((page - 1) * pageSize)
                .setMaxResults(pageSize)
                .setParameter("taskId", taskIds.size() > 0 ? taskIds : taskList)
                .setParameter("projectId", request.getProject_id())
                .setParameter("sectionId", request.getSection_id() != null ? request.getSection_id() : null)
                .setParameter("status", taskIds.size() > 0 ? taskIds : taskList);

        // Trường hợp request gửi lên không null textsearch
        if (request.getTextSearch() != null && request.getTextSearch().trim().length() > 0) {

            // Trường hợp người dùng tìm kiếm kèm sắp xếp theo điều kiện
            if (request.getSortProperty() != null && request.getSortOrder() != null) {
                searchByTextSearchAndSort(nativeQuery, request);
            }

            // Trường hợp request gửi lên  null textsearch
        } else {
            // Trường hợp người dùng sắp xếp theo điều kiện
            if (request.getSortProperty() != null && request.getSortOrder() != null) {
                searchByTextSearchAndSort(nativeQuery, request);
            }
        }

        List<Object[]> objects = query.getResultList();

        int totalElements = objects.size();

        if (!objects.isEmpty()) {
            for (Object[] object : objects) {
                Long id = Long.valueOf(object[0].toString());
                String taskName = Objects.isNull(object[1]) ? null : object[1].toString();
                BigInteger create_byBigInteger = Objects.isNull(object[2]) ? null : (BigInteger) object[2];
                Long create_by = create_byBigInteger.longValue();
                BigInteger project_idBigInteger = Objects.isNull(object[3]) ? null : (BigInteger) object[3];
                Long project_id = project_idBigInteger.longValue();
                BigInteger section_idBigInteger = Objects.isNull(object[4]) ? null : (BigInteger) object[4];
                Long section_id = section_idBigInteger.longValue();
                String description = Objects.isNull(object[5]) ? null : object[5].toString();
                String priority = Objects.isNull(object[6]) ? null : object[6].toString();
                String status = Objects.isNull(object[7]) ? null : object[7].toString();
                float estimate_time = (float) object[8];
                Timestamp start_day = Objects.isNull(object[9]) ? null : (Timestamp) object[9];
                Timestamp end_day = Objects.isNull(object[10]) ? null : (Timestamp) object[10];
                Long parent_id = Objects.isNull(object[11]) ? null : Long.valueOf(object[11].toString());
                Timestamp created_date = Objects.isNull(object[12]) ? null : (Timestamp) object[12];
                Timestamp updated_date = Objects.isNull(object[13]) ? null : (Timestamp) object[13];
                String project_name = Objects.isNull(object[14]) ? null : object[14].toString();
                String section_name = Objects.isNull(object[15]) ? null : object[15].toString();
                String assigneeName = Objects.isNull(object[16]) ? null : object[16].toString();

                TaskSearchReponse taskSearchReponse = TaskSearchReponse.builder()
                        .id(id)
                        .name(taskName)
                        .assigneeName(assigneeName)
                        .startDay(start_day.getTime())
                        .endDay(end_day.getTime())
                        .priority(priority)
                        .jobDescription(description)
                        .status(status)
                        .parentId(parent_id != null ? parent_id : -1)
                        .projectId(project_id != null ? project_id : -1)
                        .sectionId(section_id != null ? section_id : -1)
                        .projectName(project_name)
                        .sectionName(section_name)
                        .created_by(create_by)
                        .estimate_time(estimate_time)
                        .created_date(created_date != null ? created_date.getTime() : -1)
                        .updated_date( updated_date != null ? updated_date.getTime() : -1)
                        .build();

                taskSearchResponseList.add(taskSearchReponse);
            }

            if (request.getDataType() != null) {

                if (Objects.equals("today", request.getDataType()) && !CollectionUtils.isEmpty(taskSearchResponseList)) {

                    List<TaskSearchReponse> responses = getTaskSearchResponseByToday(taskSearchResponseList);
                    int totalElement = responses.size();
                    Pages pages = getPages(totalElement, pageSize, page);
                    boolean hasNext = page < pages.getTotalPages();
                    boolean hasPrevious = page > 1;
                    return new PageResponse<>(
                            responses,
                            pages.getTotalPages(),
                            pages.getTotalElements(),
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
                            pages.getTotalElements(),
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
                            pages.getTotalElements(),
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
                    pages.getTotalElements(),
                    hasNext,
                    hasPrevious
            );
        }

        List<TaskSearchReponse> taskSearchResponseIsEmpty = new ArrayList<>();
        return new PageResponse<>(taskSearchResponseIsEmpty);
    }

    private void searchByTextSearchAndSort(String nativeQuery, TaskSearchForm request) {

        if (SortPropertyEnums.valueOf(request.getSortProperty()) == SortPropertyEnums.ByEndDate) {

            if (request.getSortOrder().equals("ASC")) {
                nativeQuery += " " + "ORDER BY t.end_date ASC";
            } else {
                nativeQuery += " " + "ORDER BY t.end_date DESC";
            }

        } else if (SortPropertyEnums.valueOf(request.getSortProperty()) == SortPropertyEnums.ByCreatedDate) {

            if (request.getSortOrder().equals("ASC")) {
                nativeQuery += " " + "ORDER BY t.created_date ASC";
            } else {
                nativeQuery += " " + "ORDER BY t.created_date DESC";
            }

        } else if (SortPropertyEnums.valueOf(request.getSortProperty()) == SortPropertyEnums.ByAssignee) {

            if (request.getSortOrder().equals("ASC")) {
                nativeQuery += " " + "ORDER BY t.assignee ASC";
            } else {
                nativeQuery += " " + "ORDER BY t.assignee DESC";
            }

        } else if (SortPropertyEnums.valueOf(request.getSortProperty()) == SortPropertyEnums.ByPriority) {

            if (request.getSortOrder().equals("ASC")) {
                nativeQuery += " " + "ORDER BY t.priority ASC";
            } else {
                nativeQuery += " " + "ORDER BY t.priority DESC";
            }
        }
    }

    private Pages getPages(int totalElements, int pageSize, int page) {

        int totalPages = 0;
        if (totalElements > 0) {
            totalPages = totalElements % pageSize == 0 ?
                    totalElements / pageSize :
                    totalElements / pageSize + 1;
        }
        Pages pages = new Pages(totalElements, totalPages, pageSize, page);
        return pages;
    }

    private List<TaskSearchReponse> getTaskSearchResponseByToday(List<TaskSearchReponse> taskSearchResponseList) {

        if (taskSearchResponseList != null) {
            List<Long> taskCreatedDay = taskSearchResponseList.stream().map(TaskSearchReponse::getCreated_date).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(taskCreatedDay)) {
                LocalDate today = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int day = today.getDayOfMonth();
                List<TaskSearchReponse> taskSearchReponses = new ArrayList<>();

                for (int i = 0; i < taskCreatedDay.size(); i++) {
                    Date date = new Date(taskCreatedDay.get(i));
                    LocalDate localDateOfT = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int dayOfMonth = localDateOfT.getDayOfMonth();
                    if (dayOfMonth == day) {
                        taskSearchReponses.add(taskSearchResponseList.get(i));
                    }
                }
                if (!CollectionUtils.isEmpty(taskSearchReponses)) {
                    return taskSearchReponses;
                }
            }
        }
        return new ArrayList<>();
    }

    private List<TaskSearchReponse> getTaskSearchResponseByWeekNow(List<TaskSearchReponse> taskSearchResponseList) {

        if (taskSearchResponseList != null) {
            List<Long> taskCreatedDay = taskSearchResponseList.stream().map(TaskSearchReponse::getCreated_date).map(Long::new).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(taskCreatedDay)) {
                LocalDate dateNow = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int weekOfYearNow = dateNow.get(WeekFields.of(DayOfWeek.MONDAY, 7).weekOfYear());
                List<TaskSearchReponse> taskSearchReponses = new ArrayList<>();

                for (int i = 0; i < taskCreatedDay.size(); i++) {
                    Date date = new Date(taskCreatedDay.get(i));
                    LocalDate localDateOfT = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int weekOfYear = localDateOfT.get(WeekFields.of(DayOfWeek.MONDAY, 7).weekOfYear());
                    if (weekOfYear == weekOfYearNow) {
                        taskSearchReponses.add(taskSearchResponseList.get(i));
                    }
                }
                if (!taskSearchReponses.isEmpty()) {
                    return taskSearchReponses;
                }
            }
        }
        return new ArrayList<>();
    }

    private List<TaskSearchReponse> getTaskSearchResponseByMonthNow(List<TaskSearchReponse> taskSearchResponseList) {

        if (taskSearchResponseList != null) {
            List<Long> taskCreatedDay = taskSearchResponseList.stream().map(TaskSearchReponse::getCreated_date).map(Long::new).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(taskCreatedDay)) {
                LocalDate dateNow = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int monthNow = dateNow.getMonthValue();
                List<TaskSearchReponse> taskSearchReponses = new ArrayList<>();
                for (int i = 0; i < taskCreatedDay.size(); i++) {
                    Date date = new Date(taskCreatedDay.get(i));
                    LocalDate localDateOfT = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int month = localDateOfT.getMonthValue();
                    if (month == monthNow) {
                        taskSearchReponses.add(taskSearchResponseList.get(i));
                    }
                }
                if (!taskSearchReponses.isEmpty()) {
                    return taskSearchReponses;
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

       Long userId = resultClass.getUserId();

       validateTaskForm(taskForm);

       Task task = Task.builder()
               .name(taskForm.getName())
               .assignee(taskForm.getAssignee())
               .createdBy(userId)
               .description(taskForm.getJobDescription())
               .priority(PriorityEnums.valueOf(taskForm.getPriority()))
               .status(StatusEnums.valueOf(taskForm.getStatus()))
               .estimateTime(taskForm.getEstimate_time())
               .startDate(new Timestamp(taskForm.getStartDay()))
               .endDate(new Timestamp(taskForm.getEndDay()))
               .createdDate(new Timestamp(System.currentTimeMillis()))
               .build();
       taskRepository.save(task);

       if (taskForm.getSectionId() != null) {
           Section section = sectionRepository.findById(taskForm.getSectionId()).get();
           if (!ObjectUtils.isEmpty(section)) {
               task.setSectionId(taskForm.getSectionId());
           }
       }

       if (taskForm.getProjectId() != null) {
           Project project = projectRepository.findById(taskForm.getProjectId()).get();
           if (!ObjectUtils.isEmpty(project)) {
               task.setProjectId(taskForm.getProjectId());
           }
       }

       if (taskForm.getParentId() != null) {
           Task parent = taskRepository.findById(taskForm.getParentId()).get();
           if (!ObjectUtils.isEmpty(parent)) {
               task.setParentId(taskForm.getParentId());
           }
       }

       taskRepository.save(task);
       return task;
   }

    @Override
    public Task getById(Long taskId) {
        log.info("TaskService -> getById");

        Task task = taskRepository.findById(taskId).get();

        if (ObjectUtils.isEmpty(task)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy công việc");
        } else {
            return task;
        }
    }

    @Override
    public Task updateTask(TaskForm taskForm, Long taskId) {
        log.info("TaskService -> updateTask");

        validateUpdateTask(taskForm, taskId);

        Task task = taskRepository.findById(taskId).get();

        if (task != null) {
            task.setName(taskForm.getName() != null ? taskForm.getName() : task.getName());
            task.setDescription(taskForm.getJobDescription() != null ? taskForm.getJobDescription() : task.getDescription());
            task.setAssignee(taskForm.getAssignee() != null ? taskForm.getAssignee() : task.getAssignee());
            task.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
            task.setStartDate(new Timestamp(taskForm.getStartDay() != null ? taskForm.getStartDay() : task.getStartDate().getTime()));
            task.setEndDate(new Timestamp(taskForm.getEndDay() != null ? taskForm.getEndDay() : task.getEndDate().getTime()));
            task.setPriority(PriorityEnums.valueOf(taskForm.getPriority() != null ? taskForm.getPriority() : task.getPriority().name()));
            task.setStatus(StatusEnums.valueOf(taskForm.getStatus() != null ? taskForm.getStatus() : task.getStatus().name()));
            task.setEstimateTime((!ObjectUtils.isEmpty(taskForm.getEstimate_time()) ? taskForm.getEstimate_time() : task.getEstimateTime()));
            taskRepository.save(task);
        }
        return task;
    }

    @Override
    public Task updateStatus(Long id, String status) {
        log.info("TaskService -> updateStatus");

        Task task = taskRepository.findById(id).get();
        if (ObjectUtils.isEmpty(task)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy công việc");
        }
        task.setStatus(StatusEnums.valueOf(status));
        task.setUpdatedDate(new Timestamp(System.currentTimeMillis()));
        return taskRepository.save(task);
    }

    @Override
    public List<Task> getListTask(Long projectId, Long sectionId) {
        log.info("TaskService -> getListTask");

        Project project = null;
        Section section = null;

        if (projectId != null) {
            if (!projectRepository.existsById(projectId)) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy dự án");
            }
            project = projectRepository.findById(projectId).get();
        }

        if (sectionId != null) {
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
    public void deleteTask(Long taskId) {
        log.info("TaskService -> deleteTask");

        Task task = taskRepository.findById(taskId).get();
        if (ObjectUtils.isEmpty(task)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy công việc");
        }
        taskRepository.deleteById(taskId);
    }

    @Override
    public ByteArrayInputStream exportProgressUser(ColumExcel columExcel) {

        List<Long> listTaskIds=  taskRepository.findAllId();
        List<Timestamp> listCreatedDate=  taskRepository.findAllCreatedDate();

        List<Long> dataInMonth = getListDataByMonthNow(listCreatedDate, listTaskIds);
        List<ModelExcelUser> data = taskRepository.getListTaskFinished(dataInMonth);
        ByteArrayInputStream in = ExcelHelper.userExportToExcel(data, columExcel.getColumns());
        return in;
    }

    @Override
    public ByteArrayInputStream exportProgressTeam(ColumExcel columExcel) {
        List<Long> listTaskIds=  taskRepository.findAllId();
        List<Timestamp> listCreatedDate=  taskRepository.findAllCreatedDate();

        List<Long> dataInMonth = getListDataByMonthNow(listCreatedDate, listTaskIds);
        List<ModelExcelTeam> data = getListProjectFinished(dataInMonth);
        ByteArrayInputStream in = ExcelHelper.teamExportToExcel(data, columExcel.getColumns());
        return in;
    }

    private List<Long> getListDataByMonthNow(List<Timestamp> listCreatedDate, List<Long> listTaskIds) {

        if (listTaskIds != null && listCreatedDate != null) {
            List<Long> taskCreatedDay = listCreatedDate.stream().map(Timestamp::getTime).collect(Collectors.toList());
            if (!CollectionUtils.isEmpty(taskCreatedDay)) {
                LocalDate dateNow = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                int monthNow = dateNow.getMonthValue();
                List<Long> taskIds = new ArrayList<>();
                for (int i = 0; i < taskCreatedDay.size(); i++) {
                    Date date = new Date(taskCreatedDay.get(i));
                    LocalDate localDateOfT = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    int month = localDateOfT.getMonthValue();
                    if (month == monthNow) {
                        taskIds.add(listTaskIds.get(i));
                    }
                }
                if (!taskIds.isEmpty()) {
                    return taskIds;
                }
            }
        }
        return new ArrayList<>();
    }

    private List<ModelExcelTeam> getListProjectFinished(List<Long> dataInMonth) {

        List<ModelTeam> listData = taskRepository.findProjectFinished(dataInMonth);

        List<String> teams =  listData.stream().map(ModelTeam::getTeam).collect(Collectors.toList());
        Map<String, Integer> frequencyMap = new HashMap<>();
        for(String team : teams) {
            if(frequencyMap.containsKey(team)) {
                frequencyMap.put(team, frequencyMap.get(team) + 1);
            }else {
                frequencyMap.put(team, 1);
            }
        }

        List<ModelExcelTeam> modelExcelTeams = new ArrayList<>();

        for (int i = 0; i < frequencyMap.size() ; i++) {
            List<String> keys = frequencyMap.entrySet().stream().map(Map.Entry::getKey).collect(Collectors.toList());
            List<String> listDepartment = taskRepository.getListDepartmentByTeam(keys.get(i));
            String department = listDepartment.stream().map(Objects::toString).collect(Collectors.joining(","));
            ModelExcelTeam modelExcelTeam = ModelExcelTeam.builder()
                    .team(keys.get(i))
                    .department(department)
                    .amountProjectDone(frequencyMap.get(keys.get(i)))
                    .build();
            modelExcelTeams.add(modelExcelTeam);
        }
        return modelExcelTeams;
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

    private void validateUpdateTask(TaskForm taskForm, Long taskId) {

        HashMap<String, String> map = new HashMap<>();
        map.put("Tên dự án", taskForm.getName());
        map.put("Mô tả dự án", taskForm.getJobDescription());
        ValidateUtils.validateNullOrBlankString(map);

        if (!taskRepository.existsById(taskId)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Không tìm thấy công việc");
        }
    }
}