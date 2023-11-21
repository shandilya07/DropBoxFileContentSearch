package com.shandilya.dboxsearch.controller;

import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.shandilya.dboxsearch.entity.DropBoxFile;
import com.shandilya.dboxsearch.service.ESSearchService;
import com.shandilya.dboxsearch.service.ESIndexingService;
import com.shandilya.dboxsearch.util.DropBoxHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/es")
public class ESController {
    private static final String INDEX = "files";
    private final ESSearchService elasticSearchService;
    private final ESIndexingService esIndexingService;
    private final DropBoxHelper dropBoxHelper;

    @PostMapping("/save")
    public DropBoxFile saveFile(@RequestBody DropBoxFile fileContent) {
        return esIndexingService.persistFileContent(fileContent);
    }

    @GetMapping("/search/{keyword}")
    public List<DropBoxFile> searchIndexByKeyword(@PathVariable String keyword) throws IOException {
        SearchResponse<DropBoxFile> searchResponse =  elasticSearchService.searchIndexByTerm(INDEX, keyword);
        List<Hit<DropBoxFile>> listOfHits= searchResponse.hits().hits();
        List<DropBoxFile> listOfProducts  = new ArrayList<>();
        for(Hit<DropBoxFile> hit : listOfHits){
            listOfProducts.add(hit.source());
        }
        return listOfProducts;
    }

    @GetMapping("/all")
    public List<DropBoxFile> fetchAllInIndex() throws IOException {
        SearchResponse<DropBoxFile> searchResponse = elasticSearchService.searchEntireIndex("files");
        List<Hit<DropBoxFile>> listOfHits= searchResponse.hits().hits();
        List<DropBoxFile> listOfProducts  = new ArrayList<>();
        for(Hit<DropBoxFile> hit : listOfHits){
            listOfProducts.add(hit.source());
        }
        return listOfProducts;
    }

    @GetMapping("/purge")
    public void purgeEntireIndex() throws IOException {
        dropBoxHelper.cleanIndex("files");
    }
}