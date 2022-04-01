package com.microservice.commentservice.repository;

import com.microservice.commentservice.model.dto.CommentDto;
import com.microservice.commentservice.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {

    List<Comment> findAllByTaskId(Long id);
}
