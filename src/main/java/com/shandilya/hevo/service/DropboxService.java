package com.shandilya.hevo.service;

import com.shandilya.hevo.entity.DropBoxFile;

import java.util.List;

public interface DropboxService {
    List<String> fetchAllFolderPaths();
    List<DropBoxFile> fetchAllFilePaths();
    List<DropBoxFile> fetchAllFileContent();
}