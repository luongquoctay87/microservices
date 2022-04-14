package com.microservice.historyservice.service.impl;

import com.microservice.historyservice.VO.User;
import com.microservice.historyservice.api.form.CommentContent;
import com.microservice.historyservice.api.form.CommentForm;
import com.microservice.historyservice.api.validate.ConvertDate;
import com.microservice.historyservice.model.dto.CommentDto;
import com.microservice.historyservice.model.entity.Comment;
import com.microservice.historyservice.repository.CommentRepository;
import com.microservice.historyservice.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private RestTemplate restTemplate;

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<CommentForm> findAllByTaskId(Long _taskId) {
        List<Comment> comments = commentRepository.findAllByTaskId(_taskId);
        List<CommentForm> commentFormList = comments.stream().map(c -> convertCommentToForm(c)).collect(Collectors.toList());
        return commentFormList;

    }


    @Override
    public List<CommentForm> findAllByReplyId(Long _replyId) {
        List<Comment> comments = commentRepository.findAllByReplyId(_replyId);
        List<CommentForm> commentFormList = comments.stream().map(c -> convertCommentToForm(c)).collect(Collectors.toList());
        return commentFormList;
    }

    @Override
    public Comment save(CommentDto _commentDto) {
        Comment comment = convertDtoToComment(_commentDto);
        comment.setCreatedDate(new Date());
        comment.setEnabled(true);
        return commentRepository.save(comment);
    }

    @Override
    public boolean hide(Long _id) {
        Optional<Comment> comment = commentRepository.findById(_id);
        if (!comment.isPresent()) {
            return false;
        }
        boolean enabled = comment.get().getEnabled() == true ? false : true;
        comment.get().setEnabled(enabled);
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
        return comment;
    }

    @Override
    public CommentForm convertCommentToForm(Comment _comment) {
        CommentForm commentForm = new CommentForm();
        commentForm.setId(_comment.getId());
        commentForm.setUserId(_comment.getUserId());
        commentForm.setContent(_comment.getContent());
        commentForm.setCreatedDate(ConvertDate.convertDateToString(_comment.getCreatedDate()));
        if (_comment.getUpdatedDate() != null) {
            commentForm.setUpdatedDate(ConvertDate.convertDateToString(_comment.getUpdatedDate()));
        }
        commentForm.setCountReplyId(countReplyId(_comment.getId()));
        return commentForm;
    }

    @Override
    public Boolean edit(Long _id, CommentContent _commentContent) {
        Optional<Comment> comment = commentRepository.findById(_id);
        if (!comment.isPresent()) {
            return false;
        }
        comment.get().setUpdatedDate(ConvertDate.dateNew());
        comment.get().setId(_id);
        comment.get().setContent(_commentContent.getContent());
        if (comment.get().getUpdatedDate() == null) {
            return false;
        }
        commentRepository.save(comment.get());
        return true;
    }


    @Override
    public String findNameByUserId(Long userId) {
        User user = restTemplate.getForObject(String.format("http://localhost:9002/users/%s", userId), User.class);
        Optional<User> userOptional = Optional.ofNullable(user);
        if (!userOptional.isPresent()) {
            return null;
        }
        return userOptional.get().getUsername();
    }

    @Override
    public Long countReplyId(Long replyId) {
        return commentRepository.countReplyId(replyId);
    }

}
