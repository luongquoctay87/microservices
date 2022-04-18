package com.microservice.coreservice.domain.form;

import lombok.Data;

@Data
public class TaskSearchForm {

    private String textSearch;

//    ASC or DESC
    private String sortOrder;
//    sắp xếp theo ngày kết thúc, thời gian tạo, người được gán công việc, theo mức độ ưu tiên
    private String sortProperty;

//    Lọc theo công việc đã hoàn thành hay chưa hoàn thành
    private String filterBy;

//    Lọc công việc theo tuần , theo tháng
    private String dataType;

    private Long project_id;

    private Long section_id;
}
