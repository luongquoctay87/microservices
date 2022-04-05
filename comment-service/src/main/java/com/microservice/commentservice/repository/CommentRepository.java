package com.microservice.commentservice.repository;

import com.microservice.commentservice.model.entity.Comment;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment,Long> {

    List<Comment> findAllByTaskId(Long id);

    List<Comment> findAllByReplyId(Long replyId);
}
