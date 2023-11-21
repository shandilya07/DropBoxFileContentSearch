package com.shandilya.dboxsearch.repo;

import com.shandilya.dboxsearch.entity.DropBoxFile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FilesRepo extends ElasticsearchRepository<DropBoxFile, String> {

}