package com.shandilya.hevo.util;

import com.shandilya.hevo.service.ESIndexingService;
import com.shandilya.hevo.service.ESSearchService;
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