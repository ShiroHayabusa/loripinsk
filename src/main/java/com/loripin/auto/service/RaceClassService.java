package com.loripin.auto.service;

import com.loripin.auto.model.RaceClass;
import com.loripin.auto.repos.RaceClassRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaceClassService {
    private final
    RaceClassRepo raceClassRepo;

    public RaceClassService(RaceClassRepo raceClassRepo) {
        this.raceClassRepo = raceClassRepo;
    }

    public List<RaceClass> findAllByOrderByIdAsc() {
        return raceClassRepo.findAllByOrderByIdAsc();
    }

    public RaceClass save(RaceClass raceClass) {
        return raceClassRepo.save(raceClass);
    }
}
