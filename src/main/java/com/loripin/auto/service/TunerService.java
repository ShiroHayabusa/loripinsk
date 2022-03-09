package com.loripin.auto.service;

import com.loripin.auto.model.Tuner;
import com.loripin.auto.repos.TunerRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TunerService {
    private final
    TunerRepo tunerRepo;

    public TunerService(TunerRepo tunerRepo) {
        this.tunerRepo = tunerRepo;
    }

    public List<Tuner> findAllByOrderByName() {
        return tunerRepo.findAllByOrderByName();

    }

    public Tuner save(Tuner tuner) {
        return tunerRepo.save(tuner);
    }

    public Tuner findById(Long id) {
        return tunerRepo.getOne(id);
    }
}
