package com.microservice.historyservice.repository;

import com.microservice.historyservice.model.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface HistoryRepository extends JpaRepository<History,Long> {
    List<History> findAllByTaskId(Long id);
    @Query(value = "select pa_users.username from pa_users  where id = :userId ", nativeQuery = true)
    String findNameByUserId(Long userId);
}
