package com.shandilya.dboxsearch.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.shandilya.dboxsearch.entity.DropBoxFile;
import com.shandilya.dboxsearch.service.ESSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/search")
public class DBSearchController {

    private static final String INDEX = "files";
    private final ESSearchService esSearchService;

    @GetMapping("/{keyword}")
    public List<DropBoxFile> fetchFilesByKeyword(@PathVariable String keyword) throws IOException {
        SearchResponse<DropBoxFile> searchResponse =  esSearchService.searchIndexByTerm(INDEX, keyword);

        List<Hit<DropBoxFile>> listOfHits= searchResponse.hits().hits();
        List<DropBoxFile> dropBoxFiles  = new ArrayList<>();
        for(Hit<DropBoxFile> hit : listOfHits){
            dropBoxFiles.add(hit.source());
        }
        return dropBoxFiles;
    }
}