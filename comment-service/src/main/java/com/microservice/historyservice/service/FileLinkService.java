package com.microservice.historyservice.service;

import com.microservice.historyservice.model.entity.FileLink;

import java.util.List;
import java.util.Optional;

public interface FileLinkService {
    List<FileLink> findAllByCommentId(Long commentId);
    FileLink save(FileLink fileLink);
    void delete(Long id);
    Boolean edit(Long id, FileLink fileLink);
    Optional<FileLink>findById(Long id);
}
