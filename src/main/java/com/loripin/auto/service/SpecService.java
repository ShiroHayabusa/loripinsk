package com.loripin.auto.service;

import com.loripin.auto.model.Spec;
import com.loripin.auto.repos.SpecRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SpecService {
    private final
    SpecRepo specRepo;

    public SpecService(SpecRepo specRepo) {
        this.specRepo = specRepo;
    }

    public List<Spec> findAllByOrderByName() {
        return specRepo.findAllByOrderByName();
    }

    public Spec save(Spec spec) {
        return specRepo.save(spec);
    }

    public Spec findById(Long id) {
        return specRepo.getOne(id);
    }
}
