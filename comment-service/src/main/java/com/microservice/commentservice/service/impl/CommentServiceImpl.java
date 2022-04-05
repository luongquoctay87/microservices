package com.microservice.commentservice.service.impl;

import com.microservice.commentservice.api.form.CommentContent;
import com.microservice.commentservice.api.form.CommentForm;
import com.microservice.commentservice.api.validate.ConvertDate;
import com.microservice.commentservice.model.dto.CommentDto;
import com.microservice.commentservice.model.entity.Comment;
import com.microservice.commentservice.repository.CommentRepository;
import com.microservice.commentservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;


    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<CommentForm> findAllByTaskId(Long _taskId) {
        List<Comment> comments= commentRepository.findAllByTaskId(_taskId);
        List<CommentForm>commentFormList= comments.stream()
                .map(c -> convertCommentToForm(c))
                .collect(Collectors.toList());
        return commentFormList;

    }


    @Override
    public List<CommentForm> findAllByReplyId(Long _replyId) {
        List<Comment> comments= commentRepository.findAllByReplyId(_replyId);
        List<CommentForm>commentFormList= comments.stream()
                .map(c -> convertCommentToForm(c))
                .collect(Collectors.toList());
        return commentFormList;
    }

    @Override
    public Comment save(CommentDto _commentDto) {
        Comment comment= convertDtoToComment(_commentDto);
        comment.setCreatedDate(new Date());
        comment.setEnabled(true);
        return commentRepository.save(comment);
    }

    @Override
    public Boolean hide(Long _id) {
        Optional<Comment>comment = commentRepository.findById(_id);
        if(!comment.isPresent()){
            return false;
        }
        if(comment.get().getEnabled()){
            comment.get().setEnabled(false);
        }else {
            comment.get().setEnabled(true);
        }
        commentRepository.save(comment.get());
        return true;
    }

    @Override
    public Comment convertDtoToComment(CommentDto commentDto) {
        Comment comment = new Comment();
        comment.setUserId(commentDto.getUserId());
        comment.setTaskId(commentDto.getTaskId());
        comment.setReplyId(commentDto.getReplyId());
        comment.setContent(commentDto.getContent());
        comment.setImg(commentDto.getImg());
        return comment;
    }

    @Override
    public CommentForm convertCommentToForm(Comment _comment) {
        CommentForm commentForm = new CommentForm();
        commentForm.setId(_comment.getId());
        commentForm.setUserId(_comment.getUserId());
        commentForm.setImg(_comment.getImg());
        commentForm.setContent(_comment.getContent());
        commentForm.setCreatedDate( ConvertDate.convertDateToString(_comment.getCreatedDate()));
        if(_comment.getUpdatedDate() == null){
            return commentForm;
        }
        commentForm.setUpdatedDate( ConvertDate.convertDateToString(_comment.getUpdatedDate()));
        return commentForm;
    }

    @Override
    public Boolean edit(Long _id, CommentContent _commentContent) {
        Optional<Comment> comment = commentRepository.findById(_id);
        if(!comment.isPresent()){
            return false;
        }
        comment.get().setUpdatedDate(ConvertDate.dateNew());
        comment.get().setId(_id);
        comment.get().setContent(_commentContent.getContent());
        if(comment.get().getUpdatedDate()==null){
            return false;
        }
        commentRepository.save(comment.get());
        return true;
    }

}
