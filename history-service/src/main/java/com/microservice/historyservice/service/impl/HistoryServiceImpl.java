package com.microservice.historyservice.service.impl;

import com.microservice.historyservice.api.form.HistoryForm;
import com.microservice.historyservice.model.dao.HistoryDto;
import com.microservice.historyservice.model.entity.History;
import com.microservice.historyservice.repository.HistoryRepository;
import com.microservice.historyservice.service.HistoryService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    private HistoryRepository historyRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<HistoryForm> findByTaskId(Long id) {
        List<History> histories = historyRepository.findAllByTaskId(id);
        List<HistoryForm> historyForms = histories.stream().map(h ->convertHistoryToForm(h)).collect(Collectors.toList());
        return historyForms;
    }

    @Override
    public History save(HistoryDto historyDto) {
        return null;
    }

    @Override
    public History convertDtoToHistory(HistoryDto historyDto) {
        History history = modelMapper.map(historyDto, History.class);
        return history;
    }

    @Override
    public HistoryForm convertHistoryToForm(History history) {
        HistoryForm historyForm =  new HistoryForm();
        historyForm.setUserId(history.getUserId());
        historyForm.setActions(history.getActions());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
        String date = simpleDateFormat.format(history.getCreatedDate());
        historyForm.setCreatedDate(date);
        return historyForm;
    }

}
