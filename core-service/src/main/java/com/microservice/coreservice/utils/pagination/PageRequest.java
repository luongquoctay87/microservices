package com.microservice.coreservice.utils.pagination;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PageRequest {

    private Integer page; //page hien tai
    private Integer pageSize; //so phan tu 1 trang
    private String sortProperty; //sap xep theo truong gi
    private String sortOrder; //sap xep theo thu tu gi
}
