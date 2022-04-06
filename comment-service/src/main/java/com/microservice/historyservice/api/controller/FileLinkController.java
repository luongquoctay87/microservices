package com.microservice.historyservice.api.controller;

import com.microservice.historyservice.model.entity.FileLink;
import com.microservice.historyservice.service.FileLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/fileLinks")
public class FileLinkController {
    @Autowired
    private FileLinkService fileLinkService;

    @GetMapping("/{id}")
    public ResponseEntity<List<FileLink>> findAllByCommentId(@PathVariable("id") Long _id) {
        List<FileLink> fileLinks = fileLinkService.findAllByCommentId(_id);
        if (fileLinks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid FileLink _fileLink) {
        fileLinkService.save(_fileLink);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> edit(@PathVariable("id") Long _id, @RequestBody FileLink _fileLink){
        if(fileLinkService.edit(_id,_fileLink) == false){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void>delete(@PathVariable("id") Long _id){
        fileLinkService.delete(_id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
