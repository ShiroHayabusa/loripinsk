package com.loripin.auto.service;

import com.loripin.auto.model.BodyType;
import com.loripin.auto.repos.BodyTypeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BodyTypeService {
    private final
    BodyTypeRepo bodyTypeRepo;

    public BodyTypeService(BodyTypeRepo bodyTypeRepo) {
        this.bodyTypeRepo = bodyTypeRepo;
    }

    public List<BodyType> findAllByOrderByIdAsc() {
        return bodyTypeRepo.findAllByOrderByIdAsc();
    }

    public BodyType save(BodyType bodyType) {
        return bodyTypeRepo.save(bodyType);
    }

    public List<BodyType> findByGenerationIdAndRestyleName(Long id, String name) {
        return bodyTypeRepo.findByGenerationIdAndRestyleName(id, name);
    }

    public BodyType findById(Long id) {
        return bodyTypeRepo.getOne(id);

    }

    public void deleteById(Long id) {
        bodyTypeRepo.deleteById(id);
    }

    public List<BodyType> findByGenerationIdAndRestyleNameOrderByBodyTypeName(Long id, String name) {
        return bodyTypeRepo.findByGenerationIdAndRestyleNameOrderByBodyTypeName(id, name);
    }
}
