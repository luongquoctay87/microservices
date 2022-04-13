package com.microservice.historyservice.service;

import com.microservice.historyservice.VO.User;
import com.microservice.historyservice.api.form.HistoryForm;
import com.microservice.historyservice.model.dao.HistoryDto;
import com.microservice.historyservice.model.entity.History;

import java.util.List;

public interface HistoryService {
    List<HistoryForm> findByTaskId(Long id);
    History save(History history);
    History convertDtoToHistory(HistoryDto historyDto);
    HistoryForm convertHistoryToForm(History history);
    User findByUserId(Long id);
}
