package com.loripin.auto.service;

import com.loripin.auto.model.Engine;
import com.loripin.auto.repos.EngineRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EngineService {
    private final
    EngineRepo engineRepo;

    public EngineService(EngineRepo engineRepo) {
        this.engineRepo = engineRepo;
    }

    public List<Engine> findAllByOrderByIdAsc() {
        return engineRepo.findAllByOrderByIdAsc();
    }

    public Engine save(Engine engine) {
        return engineRepo.save(engine);
    }

    public List<Engine> findAllByOrderByNameAsc() {
        return engineRepo.findAllByOrderByNameAsc();
    }

    public Engine findById(Long id) {
        return engineRepo.getOne(id);
    }

    public void deleteById(Long id) {
        engineRepo.deleteById(id);
    }
}
