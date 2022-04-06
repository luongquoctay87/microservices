package com.microservice.historyservice.service.impl;

import com.microservice.historyservice.model.entity.FileLink;
import com.microservice.historyservice.repository.FileLinkRepository;
import com.microservice.historyservice.service.FileLinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FileLinkServiceImpl implements FileLinkService {
    @Autowired
    private FileLinkRepository fileLinkRepository;
    @Override
    public List<FileLink> findAllByCommentId(Long commentId) {
        return fileLinkRepository.findAllByCommentId(commentId);
    }

    @Override
    public FileLink save(FileLink fileLink) {
        return fileLinkRepository.save(fileLink);
    }

    @Override
    public void delete(Long id) {
        fileLinkRepository.deleteById(id);
    }

    @Override
    public Boolean edit(Long id, FileLink _fileLink) {
       Optional<FileLink> fileLink = findById(id);
       if(!fileLink.isPresent()){
           return false;
       }
       _fileLink.setId(id);
       fileLinkRepository.save(_fileLink);
        return true;
    }

    @Override
    public Optional<FileLink> findById(Long id) {
        return fileLinkRepository.findById(id);
    }
}
