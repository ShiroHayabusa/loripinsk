package com.loripin.auto.service;

import com.loripin.auto.model.BodyTypeName;
import com.loripin.auto.model.Country;
import com.loripin.auto.repos.BodyTypeNameRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BodyTypeNameService {
    private final
    BodyTypeNameRepo bodyTypeNameRepo;

    public BodyTypeNameService(BodyTypeNameRepo bodyTypeNameRepo) {
        this.bodyTypeNameRepo = bodyTypeNameRepo;
    }

    public List<BodyTypeName> findAllByOrderByIdAsc() {
        return bodyTypeNameRepo.findAllByOrderByIdAsc();
    }

    public BodyTypeName save(BodyTypeName bodyTypeName) {
        return bodyTypeNameRepo.save(bodyTypeName);
    }

    public List<BodyTypeName> findAllByOrderByNameAsc() {
        return bodyTypeNameRepo.findAllByOrderByNameAsc();
    }

    public BodyTypeName findById(Long id) {
        return bodyTypeNameRepo.getOne(id);
    }
    public void deleteById(Long id) {
        bodyTypeNameRepo.deleteById(id);
    }
}
