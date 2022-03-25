package com.loripin.auto.service;

import com.loripin.auto.model.Modification;
import com.loripin.auto.model.Photo;
import com.loripin.auto.repos.FileStorage;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class FileUpload {
    private final
    FileStorage fileStorage;
    private final
    FileStorageImpl fileStorageImpl;
    private final
    ModificationService modificationService;
    private final
    PhotoService photoService;

    public FileUpload(PhotoService photoService, ModificationService modificationService, FileStorageImpl fileStorageImpl, FileStorage fileStorage) {
        this.photoService = photoService;
        this.modificationService = modificationService;
        this.fileStorageImpl = fileStorageImpl;
        this.fileStorage = fileStorage;
    }
    private static Photo photo;

    public Photo fileUpload(@RequestParam("file") MultipartFile file)
            throws IOException {
        if (file != null) {
            String uuidFile = UUID.randomUUID().toString();
            String fileExtension = FilenameUtils.getExtension(file.getOriginalFilename());
            String resultFilename = uuidFile + "." + fileExtension;
            file.transferTo(new File(fileStorageImpl.uploadDir2 + "/" + resultFilename));
            photo = new Photo();
            photo.setName(resultFilename);
            photoService.save(photo);
        }
        return photo;
    }

    public List<Photo> multiFilesUpload(@RequestParam("files") MultipartFile[] files) {


        try {
            List<String> fileNames = Arrays.asList(files)
                    .stream()
                    .map(file -> {
                        fileStorage.store(file);
                        return file.getOriginalFilename();
                    })
                    .collect(Collectors.toList());

        } catch (Exception e) {
            throw new RuntimeException("FAIL! -> message = " + e.getMessage());
        }
        List<Photo> photos = new ArrayList<>();

        for (String fileName : fileStorageImpl.fileNames) {
            Photo photo1 = new Photo();
            photo1.setName(fileName);
            photoService.save(photo1);
            photos.add(photo1);
        }
        fileStorageImpl.fileNames.clear();
        return photos;
    }
}
