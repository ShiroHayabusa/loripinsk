package com.loripin.auto.service;

import com.loripin.auto.model.Generation;
import com.loripin.auto.repos.GenerationRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenerationService {
    private final
    GenerationRepo generationRepo;

    public GenerationService(GenerationRepo generationRepo) {
        this.generationRepo = generationRepo;
    }

    public List<Generation> findAll() {
        return generationRepo.findAll();
    }

    public Generation saveGeneration(Generation generation) {
        return generationRepo.save(generation);
    }

    public Generation findById(Long id) {
        return generationRepo.getOne(id);
    }

    public void deleteById(Long id) {
        generationRepo.deleteById(id);
    }

    public List<Generation> findAllByOrderByIdAsc() {
        return generationRepo.findAllByOrderByIdAsc();
    }

    public List<Generation> findByCarmodelIdOrderByNameAsc(Long id) {
        return generationRepo.findByCarmodelIdOrderByNameAsc(id);
    }

    public List<Generation> findAllByOrderByManufacturerAsc() {
        return generationRepo.findAllByOrderByManufacturerAsc();
    }

    public List<Generation> findByCarmodelIdOrderByYearsAsc(Long id) {
        return generationRepo.findByCarmodelIdOrderByYearsAsc(id);
    }
}
