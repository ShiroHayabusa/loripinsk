package com.loripin.auto.service;

import com.loripin.auto.model.Country;
import com.loripin.auto.model.Drive;
import com.loripin.auto.repos.DriveRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriveService {
    private final
    DriveRepo driveRepo;

    public DriveService(DriveRepo driveRepo) {
        this.driveRepo = driveRepo;
    }

    public List<Drive> findAllByOrderByIdAsc() {
        return driveRepo.findAllByOrderByIdAsc();
    }

    public Drive save(Drive drive) {
        return driveRepo.save(drive);
    }

    public Drive findById(Integer id) {
        return driveRepo.getOne(id);
    }
    public void deleteById(Integer id) {
        driveRepo.deleteById(id);
    }
}
