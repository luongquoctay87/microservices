package com.microservice.historyservice.repository;

import com.microservice.historyservice.model.entity.FileLink;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FileLinkRepository extends JpaRepository<FileLink,Long> {
    List<FileLink> findAllByCommentId(Long commentId);
}
