package com.microservice.coreservice.utils.pagination;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

public class PaginationUtils {

    public static Pageable getPageable(PageRequest pageReq) {
        return org.springframework.data.domain.PageRequest.of(
                pageReq.getPage(),
                pageReq.getPageSize(),
                Sort.by(pageReq.getSortOrder(), pageReq.getSortProperty()));
    }
}
