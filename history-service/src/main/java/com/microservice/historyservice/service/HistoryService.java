package com.microservice.historyservice.service;

import com.microservice.historyservice.api.form.HistoryForm;
import com.microservice.historyservice.model.dao.HistoryDto;
import com.microservice.historyservice.model.entity.History;

import java.util.List;

public interface HistoryService {
    List<HistoryForm> findByTaskId(Long id);
    History save(HistoryDto historyDto);
    History convertDtoToHistory(HistoryDto historyDto);
    HistoryForm convertHistoryToForm(History history);
}
