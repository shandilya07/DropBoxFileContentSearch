package com.shandilya.hevo.service;

import com.shandilya.hevo.entity.DropBoxFile;
import com.shandilya.hevo.repo.FilesRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ESIndexingService {
    private final FilesRepo filesRepo;

    public DropBoxFile persistFileContent(final DropBoxFile file) {
        return filesRepo.save(file);
    }

    public void deleteFileContent(final String id) {
        filesRepo.deleteById(id);
    }
}