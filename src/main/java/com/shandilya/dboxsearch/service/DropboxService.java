package com.shandilya.dboxsearch.service;

import com.shandilya.dboxsearch.entity.DropBoxFile;

import java.util.List;

public interface DropboxService {
    List<String> fetchAllFolderPaths();
    List<DropBoxFile> fetchAllFilePaths();
    List<DropBoxFile> fetchAllFileContent();
}