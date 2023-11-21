package com.shandilya.dboxsearch.service;

import com.shandilya.dboxsearch.entity.DropBoxFile;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class BackgroundJob {

    private static final String INDEX = "files";
    private final DropboxService dropboxService;
    private final ESSearchService esSearchService;
    private final ESIndexingService esIndexingService;

    @Scheduled(fixedRate = 15000)
    public void indexCleanup() throws IOException {
        // Make call to the DropBox and find the current list of file IDs -- 1
        List<String> dropBoxFileIds = dropboxService.fetchAllFileContent()
                .stream()
                .map(DropBoxFile::getId)
                .toList();
        log.info("indexCleanup Drop box file IDs are {} with count {}", String.join(",", dropBoxFileIds), dropBoxFileIds.size());

        // Make call to the ES to find the current list of the IDs in the Index -- 2
        List<String> indexIDs = esSearchService.fetchIDsByIndex(INDEX);
        log.info("indexCleanup ES Index IDs are {} with count {}", String.join(",", indexIDs), indexIDs.size());

        // Using the IDs obtained in 1 & 2, find out the difference, refresh the Index
        if (dropBoxFileIds.size() < indexIDs.size()) {
            // Some files in drop box have been deleted
            List<String> idsToBeRemoved = indexIDs.stream()
                    .filter(id -> !dropBoxFileIds.contains(id))
                    .toList();

            // Remove these ids from the index
            for (String id : idsToBeRemoved) {
                esIndexingService.deleteFileContent(id);
            }
            log.info("indexCleanup These IDs {} have been removed from Index {} ", String.join(",", idsToBeRemoved), INDEX);
        }
    }

    @Scheduled(fixedRate = 10000)
    public void buildIndex() throws IOException {
        // Make call to the DropBox and find the current list of file IDs -- 1
        List<String> dropBoxFileIds = dropboxService.fetchAllFileContent()
                .stream()
                .map(DropBoxFile::getId)
                .toList();
        log.info("@buildIndex Drop box file IDs are {} with count {}", String.join(",", dropBoxFileIds), dropBoxFileIds.size());

        // Make call to the ES to find the current list of the IDs in the Index -- 2
        List<String> indexIDs = esSearchService.fetchIDsByIndex(INDEX);
        log.info("@buildIndex ES Index IDs are {} with count {}", String.join(",", indexIDs), indexIDs.size());

        List<DropBoxFile> files = new ArrayList<>();

        if (dropBoxFileIds.size() > indexIDs.size()) {
            List<String> fileIDsToBeAdded = dropBoxFileIds.stream()
                    .filter(id -> !indexIDs.contains(id))
                    .toList();
            log.info("@buildIndex file IDs to be added are {} with count {} ", String.join(",", fileIDsToBeAdded), fileIDsToBeAdded.size());
            files = dropboxService.fetchAllFileContent().stream()
                    .filter(f -> fileIDsToBeAdded.contains(f.getId()))
                    .toList();
        }

        for (DropBoxFile file : files) {
            esIndexingService.persistFileContent(file);
            log.info("@buildIndex File with ID {} indexed ", file.getId());
        }
    }
}