package com.microservice.commentservice.service.imol;

import com.microservice.commentservice.model.dto.CommentDto;
import com.microservice.commentservice.model.entity.Comment;
import com.microservice.commentservice.repository.CommentRepository;
import com.microservice.commentservice.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Optional<Comment> findById(Long id) {
        return commentRepository.findById(id);
    }

    @Override
    public List<CommentDto> findAllByTaskId(Long taskId) {
       List<Comment> comments= commentRepository.findAllByTaskId(taskId);
       List<CommentDto>commentDtos= comments.stream()
               .map(c -> convertCommentToDto(c))
               .collect(Collectors.toList());
        return commentDtos;
    }


    @Override
    public List<CommentDto> findAllByReplyId(Long replyId) {
        List<Comment> comments= commentRepository.findAllByTaskId(replyId);
        List<CommentDto>commentDtos= comments.stream()
                .map(c -> convertCommentToDto(c))
                .collect(Collectors.toList());
        return commentDtos;
    }

    @Override
    public Comment save(CommentDto commentDto) {
        Comment commentSave= convertDtoToComment(commentDto);
        return commentRepository.save(commentSave);
    }

    @Override
    public Boolean hide(Long id) {
        Optional<Comment>comment = commentRepository.findById(id);
        if(!comment.isPresent()){
            return false;
        }
        if(comment.get().getEnabled()){
            comment.get().setEnabled(false);
        }else {
            comment.get().setEnabled(true);
        }

        return true;
    }

    @Override
    public Comment convertDtoToComment(CommentDto commentDto) {
        Comment comment = modelMapper.map(commentDto,Comment.class);
        return comment;
    }

    @Override
    public CommentDto convertCommentToDto(Comment comment) {
        CommentDto commentDto= modelMapper.map(comment,CommentDto.class);
        return commentDto;
    }

}
