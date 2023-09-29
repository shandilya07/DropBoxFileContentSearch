package com.shandilya.hevo.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import co.elastic.clients.elasticsearch._types.query_dsl.Query;
import co.elastic.clients.elasticsearch.core.SearchResponse;
import co.elastic.clients.elasticsearch.core.search.Hit;
import com.shandilya.hevo.entity.DropBoxFile;
import com.shandilya.hevo.util.ElasticSearchUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Service
@RequiredArgsConstructor
public class ESSearchService {

    private final ElasticsearchClient esClient;

    public SearchResponse<DropBoxFile> searchEntireIndex(final String index) throws IOException {
        Supplier<Query> querySupplier = ElasticSearchUtil.supplierSearchAll();
        return esClient.search(s -> s.index(index).query(querySupplier.get()), DropBoxFile.class);
    }

    public SearchResponse<DropBoxFile> searchIndexByTerm(final String index, final String fieldValue) throws IOException {
        Supplier<Query> supplier  = ElasticSearchUtil.supplierWithFieldName(fieldValue);
        SearchResponse<DropBoxFile> searchResponse = esClient.search(s->s.index(index).query(supplier.get()), DropBoxFile.class);
        System.out.println("elasticsearch query is "+supplier.get().toString());
        return searchResponse;
    }

    public List<String> fetchIDsByIndex(final String index) throws IOException {
        SearchResponse<DropBoxFile> searchResponse = searchEntireIndex(index);
        List<Hit<DropBoxFile>> listOfHits= searchResponse.hits().hits();
        List<DropBoxFile> files  = new ArrayList<>();
        for(Hit<DropBoxFile> hit : listOfHits){
            files.add(hit.source());
        }
        return files.stream().map(DropBoxFile::getId).toList();
    }
}