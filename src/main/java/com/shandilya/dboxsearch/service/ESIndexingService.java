package com.shandilya.dboxsearch.service;

import com.shandilya.dboxsearch.entity.DropBoxFile;
import com.shandilya.dboxsearch.repo.FilesRepo;
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