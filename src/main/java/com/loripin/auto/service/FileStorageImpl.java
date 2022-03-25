package com.loripin.auto.service;

import com.loripin.auto.repos.FileStorage;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class FileStorageImpl implements FileStorage {

    public File uploadDir2;

    public List<String> fileNames = new ArrayList<>();

    public void store(MultipartFile file){
        String path2 =  uploadDir2.toString();
        try
        {
            Path path = Paths.get(path2).resolve(UUID.randomUUID().toString() + "." +
                    FilenameUtils.getExtension(file.getOriginalFilename()));
            String fileName = path.getFileName().toString();
            fileNames.add(fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
    }
}
