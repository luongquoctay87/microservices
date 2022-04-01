package com.microservice.commentservice.service;

import com.microservice.commentservice.model.dto.CommentDto;
import com.microservice.commentservice.model.entity.Comment;

import java.util.List;
import java.util.Optional;

public interface CommentService {
    Optional<Comment> findById(Long id);
    List<CommentDto>findAllByTaskId(Long taskId);
    List<CommentDto>findAllByReplyId(Long replyId);
    Comment save(CommentDto commentDto);
    Boolean hide(Long id);
    Comment convertDtoToComment(CommentDto commentDto);
    CommentDto convertCommentToDto(Comment comment);

}
