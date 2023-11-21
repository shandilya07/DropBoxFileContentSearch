package com.shandilya.dboxsearch.util;

import com.shandilya.dboxsearch.service.ESIndexingService;
import com.shandilya.dboxsearch.service.ESSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class DropBoxHelper {

    private final ESSearchService esSearchService;
    private final ESIndexingService esIndexingService;

    public void cleanIndex(final String index) throws IOException {
        for (String id : esSearchService.fetchIDsByIndex(index)) {
            esIndexingService.deleteFileContent(id);
        }
    }
}