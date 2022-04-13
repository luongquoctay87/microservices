package com.microservice.historyservice.api.controller;

import com.microservice.historyservice.VO.User;
import com.microservice.historyservice.api.form.HistoryForm;
import com.microservice.historyservice.model.dao.HistoryDto;
import com.microservice.historyservice.model.entity.History;
import com.microservice.historyservice.service.impl.HistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/histories")
public class HistoryController {
    @Autowired
    private HistoryServiceImpl historyService;

    @GetMapping("/{id}")
    public ResponseEntity<List<HistoryForm>> findAllHistoryByTaskId(@PathVariable("id") Long _id) {
        List<HistoryForm> historyForms = historyService.findByTaskId(_id);
        if (historyForms.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(historyForms, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Void> save(@RequestParam("index") int _index,
                                       @RequestParam("content") String _content,
                                       @RequestBody HistoryDto historyDto) {
        Optional<User> user = Optional.ofNullable(historyService.findByUserId(historyDto.getUserId()));
        if(!user.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        String userName = user.get().getUsername();
        History history = historyService.convertDtoToHistory(historyDto);
        switch (_index) {
            case 1:
                history.setActions(userName + " đã tạo công việc này");
                break;
            case 2:
                history.setActions(userName + " đã gán công việc này cho " + _content);
                break;
            case 3:
                history.setActions(userName + " đã thay đổi mức ưu tiên " + _content);
                break;
            case 4:
                history.setActions(userName + " đã thay đổi mô tả công việc ");
                break;
            case 5:
                history.setActions(userName + " đã thay đổi trạng thái công việc " + _content);
                break;
            case 6:
                history.setActions(userName + " đã thay đổi ngày hết hạn thành ngày " + _content);
                break;
            case 7:
                history.setActions(userName + " đã thay đổi ước tính thời gian " + _content);
                break;
        }
        history.setCreatedDate(new Date());
        historyService.save(history);
        return new ResponseEntity<>(HttpStatus.OK);

    }
}
