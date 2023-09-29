package com.shandilya.hevo.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.elasticsearch.annotations.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(indexName = "files")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DropBoxFile {
    private String id;
    private String filePath;
    private String fileContent;
}