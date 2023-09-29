package com.shandilya.hevo.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.shandilya.hevo.entity.DropBoxFile;
import com.shandilya.hevo.service.DropboxService;
import com.shandilya.hevo.service.ESSearchService;
import com.shandilya.hevo.service.ESIndexingService;
import com.shandilya.hevo.util.DropBoxHelper;
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

    private final DropBoxHelper dropBoxHelper;
    private final DropboxService dropboxService;
    private final ESSearchService esSearchService;
    private final ESIndexingService esIndexingService;

    @GetMapping("/{keyword}")
    public List<DropBoxFile> matchAllProductsWithName(@PathVariable String keyword) throws IOException {
        // 1. From DropBoxService fetch all the file content
        //List<DropBoxFile> files = dropboxService.fetchAllFileContent();

        // 2. Use FileContentSearchService to prepare the index
        // Clean the index to ensure freshness of the content
        //dropBoxHelper.cleanIndex(INDEX);
/*
        for (DropBoxFile file : files) {
            esIndexingService.persistFileContent(file);
        }*/

        // 3. Use FileContentSearchService to search for the file
        SearchResponse<DropBoxFile> searchResponse =  esSearchService.searchIndexByTerm(INDEX, keyword);
        System.out.println(searchResponse.hits().hits().toString());

        List<Hit<DropBoxFile>> listOfHits= searchResponse.hits().hits();
        List<DropBoxFile> listOfProducts  = new ArrayList<>();
        for(Hit<DropBoxFile> hit : listOfHits){
            listOfProducts.add(hit.source());
        }
        return listOfProducts;
    }
}