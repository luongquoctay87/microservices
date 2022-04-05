package com.microservice.historyservice.api.controller;

import com.microservice.historyservice.api.form.HistoryForm;
import com.microservice.historyservice.model.dao.HistoryDto;
import com.microservice.historyservice.model.entity.History;
import com.microservice.historyservice.service.impl.HistoryServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/histories")
public class HistoryController {
    @Autowired
    private HistoryServiceImpl historyService;

    @GetMapping("/{id}")
    public ResponseEntity<List<HistoryForm>> findAllByTaskId(@PathVariable("id") Long _id) {
        List<HistoryForm> historyForms = historyService.findByTaskId(_id);
        if (historyForms.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(historyForms, HttpStatus.OK);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> save(@RequestParam("order") int _order,
                                       @RequestParam("change") String _change,
                                       @RequestBody HistoryDto historyDto) {
        String userName = historyService.findNameByUserId(historyDto.getUserId());
        History history = historyService.convertDtoToHistory(historyDto);
        switch (_order) {
            case 1:
                history.setActions(userName + " đã tạo công việc này");
                break;
            case 2:
                history.setActions(userName + " đã gán công việc này cho " + _change);
                break;
            case 3:
                history.setActions(userName + " đã thay đổi mức ưu tiên " + _change);
                break;
            case 4:
                history.setActions(userName + " đã thay đổi mô tả công việc ");
                break;
            case 5:
                history.setActions(userName + " đã thay đổi trạng thái công việc " + _change);
                break;
            case 6:
                history.setActions(userName + " đã thay đổi ngày hết hạn thành ngày " + _change);
                break;
            case 7:
                history.setActions(userName + " đã thay đổi ước tính thời gian " + _change);
                break;
        }
        history.setCreatedDate(new Date());
        historyService.save(history);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Lịch sử đã được lưu");

    }
}
