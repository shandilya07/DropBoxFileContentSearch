package com.shandilya.hevo.repo;

import com.shandilya.hevo.entity.DropBoxFile;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface FilesRepo extends ElasticsearchRepository<DropBoxFile, String> {

}