package com.microservice.coreservice.utils.pagination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
public class Pages {
    private int totalElements;
    private int totalPages;
    private int pageSize;
    private int page;

    public Pages(int totalElements, Integer pageSize, Integer page , int totalPages) {
        this.totalElements = totalElements;
        this.pageSize = pageSize;
        this.page = page;
        this.totalPages = totalPages;
    }
}
