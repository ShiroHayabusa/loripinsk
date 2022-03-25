package com.loripin.auto.service;

import com.loripin.auto.model.Photo;
import com.loripin.auto.repos.PhotoRepo;
import org.springframework.stereotype.Service;

@Service
public class PhotoService {
    private final
    PhotoRepo photoRepo;

    public PhotoService(PhotoRepo photoRepo) {
        this.photoRepo = photoRepo;
    }
    public Photo save(Photo photo){
        return photoRepo.save(photo);
    }
}
