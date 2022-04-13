package com.microservice.historyservice.repository;

import com.microservice.historyservice.model.entity.Comment;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends PagingAndSortingRepository<Comment,Long> {
    @Query(value = "SELECT * FROM pa_task_management.pa_comments where task_id = :id  AND enabled = 1 ORDER BY  id DESC", nativeQuery = true)
    List<Comment> findAllByTaskId(Long id);

    @Query(value = "SELECT * FROM pa_task_management.pa_comments where reply_id = :replyId AND enabled = 1 ORDER BY  id DESC", nativeQuery = true)
    List<Comment> findAllByReplyId(Long replyId);

    @Query(value = "select pa_users.username from pa_users  where id = :userId ", nativeQuery = true)
    String findNameByUserId(Long userId);

    @Query(value = "SELECT COUNT(*) FROM pa_task_management.pa_comments Where reply_id = :replyId", nativeQuery = true)
    Long countReplyId(Long replyId);

}
