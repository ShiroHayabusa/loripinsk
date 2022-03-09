package com.loripin.auto.service;

import com.loripin.auto.model.GearBox;
import com.loripin.auto.repos.GearBoxRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GearBoxService {
    private final
    GearBoxRepo gearBoxRepo;

    public GearBoxService(GearBoxRepo gearBoxRepo) {
        this.gearBoxRepo = gearBoxRepo;
    }

    public List<GearBox> findAllByOrderByIdAsc() {
        return gearBoxRepo.findAllByOrderByIdAsc();
    }

    public GearBox save(GearBox gearBox) {
        return gearBoxRepo.save(gearBox);
    }

    public List<GearBox> findAllByOrderByNameAsc() {
        return gearBoxRepo.findAllByOrderByNameAsc();
    }
}
