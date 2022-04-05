package com.microservice.commentservice.service;

import com.microservice.commentservice.api.form.CommentContent;
import com.microservice.commentservice.api.form.CommentForm;
import com.microservice.commentservice.model.dto.CommentDto;
import com.microservice.commentservice.model.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(Long id);
    List<CommentForm>findAllByTaskId(Long taskId);
    List<CommentForm>findAllByReplyId(Long replyId);
    Comment save(CommentDto commentDto);
    Boolean hide(Long id);
    Comment convertDtoToComment(CommentDto commentDto);
    CommentForm convertCommentToForm(Comment comment);
    Boolean edit(Long id, CommentContent commentContent);

}
