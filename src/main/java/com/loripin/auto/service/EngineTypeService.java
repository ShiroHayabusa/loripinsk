package com.loripin.auto.service;

import com.loripin.auto.model.Country;
import com.loripin.auto.model.EngineType;
import com.loripin.auto.repos.EngineTypeRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EngineTypeService {
    private final
    EngineTypeRepo engineTypeRepo;

    public EngineTypeService(EngineTypeRepo engineTypeRepo) {
        this.engineTypeRepo = engineTypeRepo;
    }

    public List<EngineType> findAllByOrderByIdAsc() {
        return engineTypeRepo.findAllByOrderByIdAsc();
    }

    public EngineType save(EngineType engineType) {
        return engineTypeRepo.save(engineType);
    }

    public List<EngineType> findAllByOrderByNameAsc() {
        return engineTypeRepo.findAllByOrderByNameAsc();
    }

    public EngineType findById(Integer id) {
        return engineTypeRepo.getOne(id);
    }
    public void deleteById(Integer id) {
        engineTypeRepo.deleteById(id);
    }
}
