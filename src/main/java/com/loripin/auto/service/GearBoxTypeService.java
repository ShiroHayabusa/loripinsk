package com.loripin.auto.service;

import com.loripin.auto.model.Country;
import com.loripin.auto.model.GearBoxType;
import com.loripin.auto.repos.GearBoxTypeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GearBoxTypeService {
    private final
    GearBoxTypeRepo gearBoxTypeRepo;

    public GearBoxTypeService(GearBoxTypeRepo gearBoxTypeRepo) {
        this.gearBoxTypeRepo = gearBoxTypeRepo;
    }

    public List<GearBoxType> findAllByOrderByIdAsc() {
        return gearBoxTypeRepo.findAllByOrderByIdAsc();
    }

    public GearBoxType save(GearBoxType gearBoxType) {
        return gearBoxTypeRepo.save(gearBoxType);
    }

    public List<GearBoxType> findAllByOrderByNameAsc() {
        return gearBoxTypeRepo.findAllByOrderByNameAsc();
    }

    public GearBoxType findById(Long id) {
        return gearBoxTypeRepo.getOne(id);
    }
    public void deleteById(Long id) {
        gearBoxTypeRepo.deleteById(id);
    }
}
