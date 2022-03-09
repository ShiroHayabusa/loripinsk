package com.loripin.auto.service;

import com.loripin.auto.model.Engine;
import com.loripin.auto.model.Modification;
import com.loripin.auto.repos.ModificationRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ModificationService {
    private final
    ModificationRepo modificationRepo;

    public ModificationService(ModificationRepo modificationRepo) {
        this.modificationRepo = modificationRepo;
    }

    public Modification saveModification(Modification modification) {
        return modificationRepo.save(modification);
    }

    public Modification findById(Long id) {
        return modificationRepo.getOne(id);
    }

    public void deleteById(Long id) {
        modificationRepo.deleteById(id);
    }

    public List<Modification> findAll() {
        return modificationRepo.findAll();
    }

    public List<Modification> findAllByOrderByIdAsc() {
        return modificationRepo.findAllByOrderByIdAsc();
    }

    public List<Modification> findByBodyTypeIdOrderByName(Long id) {
        return modificationRepo.findByBodyTypeIdOrderByName(id);
    }

    public List<Modification> findByTunerId(Long id) {
        return modificationRepo.findByTunerId(id);
    }

    public List<Modification> findByTuner(String s) {
        return modificationRepo.findByTuner(s);
    }

    public List<Modification> findAllByOrderByIdDesc() {
        return modificationRepo.findAllByOrderByIdDesc();
    }

    public List<Modification> findBySeriesIdOrderByNameAsc(Long id) {
        return modificationRepo.findBySeriesIdOrderByNameAsc(id);
    }

    public List<Modification> findByEngineIdOrderByIdDesc(Long id) {
        return modificationRepo.findByEngineIdOrderByIdDesc(id);
    }
}
