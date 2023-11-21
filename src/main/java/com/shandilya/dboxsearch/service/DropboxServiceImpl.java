package com.shandilya.dboxsearch.service;

import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.FolderMetadata;
import com.dropbox.core.v2.files.ListFolderResult;
import com.dropbox.core.v2.files.Metadata;
import com.shandilya.dboxsearch.entity.DropBoxFile;
import com.shandilya.dboxsearch.exception.DropboxException;
import com.shandilya.dboxsearch.util.StringUtil;
import lombok.SneakyThrows;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.*;

@Service
public class DropboxServiceImpl implements DropboxService {

    private final DbxClientV2 client;

    public DropboxServiceImpl(DbxClientV2 client) {
        this.client = client;
    }

    private <T> T handleDropboxAction(DropBoxActionHandler<T> action, String exceptionMessage) {
        try {
            return action.perform();
        } catch (Exception e) {
            String messageWithCause = String.format("%s with cause: %s", exceptionMessage, e.getMessage());
            throw new DropboxException(messageWithCause, e);
        }
    }

    @Override
    public List<String> fetchAllFolderPaths() {
        List<String> folders = new ArrayList<>();
        String folderPath = ""; // For root folder in DBox
        ListFolderResult listFolderResult = handleDropboxAction(() -> client.files().listFolder(folderPath),
                "Error getting folder details for the root folder");

        for (Metadata entry : listFolderResult.getEntries()) {
            if (entry instanceof FolderMetadata folder) {
                folders.add(folder.getName());
            }
        }
        return folders;
    }

    @Override
    public List<DropBoxFile> fetchAllFilePaths() {
        List<DropBoxFile> files = new ArrayList<>();
        List<String> folders = fetchAllFolderPaths();

        for (String folder : folders) {
            ListFolderResult folderResult = handleDropboxAction(() -> client.files().listFolder("/" + folder),
                    String.format("Error in listing files in the folder %s", folder));

            for (Metadata entry : folderResult.getEntries()) {
                if (entry instanceof FileMetadata file) {
                    DropBoxFile dbFile = new DropBoxFile();
                    dbFile.setId(((FileMetadata) entry).getId());
                    dbFile.setFilePath("/" + folder + "/" + file.getName());
                    files.add(dbFile);
                }
            }
        }
        return files;
    }

    @SneakyThrows
    public List<DropBoxFile> fetchAllFileContent() {
        List<DropBoxFile> files = new ArrayList<>();

        for (DropBoxFile file : fetchAllFilePaths()) {
            InputStream inputStream = handleDropboxAction(() -> client.files().download(file.getFilePath()).getInputStream(),
                    String.format("Error fetching file content for file %s", file.getFilePath()));

            AutoDetectParser parser = new AutoDetectParser();
            BodyContentHandler handler = new BodyContentHandler();
            org.apache.tika.metadata.Metadata metadata = new org.apache.tika.metadata.Metadata();
            parser.parse(inputStream, handler, metadata, new ParseContext());
            String fileContent = handler.toString();
            file.setFileContent(StringUtil.tokenize(fileContent));
            files.add(file);
        }
        return files;
    }
}
