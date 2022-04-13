package com.microservice.historyservice.repository;

import com.microservice.historyservice.model.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Long> {
    @Query(value = "FROM Comment where taskId = :id  AND enabled = 1 ORDER BY  id DESC")
    List<Comment> findAllByTaskId(Long id);

    @Query(value = "FROM Comment where replyId = :replyId AND enabled = 1 ORDER BY  id DESC")
    List<Comment> findAllByReplyId(Long replyId);

    @Query(value = "SELECT COUNT(*) FROM Comment Where replyId = :replyId")
    Long countReplyId(Long replyId);

}
