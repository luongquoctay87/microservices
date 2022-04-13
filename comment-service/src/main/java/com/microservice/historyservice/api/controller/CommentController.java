package com.microservice.historyservice.api.controller;

import com.microservice.historyservice.api.form.CommentContent;
import com.microservice.historyservice.api.form.CommentForm;
import com.microservice.historyservice.model.dto.CommentDto;
import com.microservice.historyservice.model.entity.Comment;
import com.microservice.historyservice.service.impl.CommentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/comments")
@CrossOrigin("*")
public class CommentController {
    @Autowired
    private CommentServiceImpl commentService;

    @GetMapping("/{id}")
    public ResponseEntity<CommentForm> findById(@PathVariable("id") Long _id) {
        Optional<Comment> commentOptional = commentService.findById(_id);
        if (!commentOptional.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        CommentForm commentForm = commentService.convertCommentToForm(commentOptional.get());
        return new ResponseEntity<>(commentForm, HttpStatus.OK);
    }


    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> save(@Valid @RequestBody CommentDto _commentDto) {
        Comment comment = commentService.save(_commentDto);
        return ResponseEntity.status(HttpStatus.OK)
                .body("Tạo thành công");
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> edit(@PathVariable("id") Long _id, @RequestBody @Valid CommentContent _commentContent, BindingResult bindingResult) {
        if (!commentService.edit(_id, _commentContent)) {
            return ResponseEntity.status(HttpStatus.NOT_IMPLEMENTED)
                    .body("ERROR trong quá trình tạo ngày hoăc không tìm thấy id");
        }
        return ResponseEntity.status(HttpStatus.OK)
                .body("Edit thành công");
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long _id) {
        Boolean check = commentService.hide(_id);
        if (check) {
            return ResponseEntity.status(HttpStatus.OK)
                    .body("Edit thành công");
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("ERROR không tìm thấy id ");

    }

    @GetMapping("/reply/{id}")
    public ResponseEntity<List<CommentForm>> findAllByReplyId(@PathVariable("id") Long _id) {
        List<CommentForm> comments = commentService.findAllByReplyId(_id);
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comments, HttpStatus.OK);

    }

    @GetMapping("/task/{id}")
    public ResponseEntity<List<CommentForm>> findAllByTaskId(@PathVariable("id") Long _id) {
        List<CommentForm> comments = commentService.findAllByTaskId(_id);
        if (comments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(comments, HttpStatus.OK);
    }
    @GetMapping("/user/{id}")
    public ResponseEntity<String> findNameById(@PathVariable("id") Long _id) {
        String name= commentService.findNameByUserId(_id);
        if (name.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(name, HttpStatus.OK);
    }
}