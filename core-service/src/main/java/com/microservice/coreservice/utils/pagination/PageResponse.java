package com.microservice.coreservice.utils.pagination;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class PageResponse<T> {
    private List<T> data;
    private int totalPages;
    private int totalElements;
    private boolean hasNext;
    private boolean hasPrevious;

    public PageResponse() {
    }

    public PageResponse(List<T> data) {
        this.data = new ArrayList<>();
        this.totalPages = 0;
        this.totalElements = 0;
        this.hasNext = false;
    }

    public PageResponse(List<T> data, int totalPages, int totalElements, boolean hasNext, boolean hasPrevious) {
        this.data = data;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
        this.hasNext = hasNext;
        this.hasPrevious = hasPrevious;
    }
}
