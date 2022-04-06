package com.microservice.historyservice.repository;

import com.microservice.historyservice.model.entity.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment,Long> {
    @Query(value = "SELECT * FROM pa_task_management.pa_comments where task_id = :id  AND enabled = 0 ORDER BY  id DESC", nativeQuery = true)
    List<Comment> findAllByTaskId(Long id);

    @Query(value = "SELECT * FROM pa_task_management.pa_comments where reply_id = :id AND enabled = 0 ORDER BY  id DESC", nativeQuery = true)
    List<Comment> findAllByReplyId(Long replyId);
}
