package com.loripin.auto.service;

import com.loripin.auto.model.RaceResult;
import com.loripin.auto.repos.RaceResultRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaceResultService {
    private final
    RaceResultRepo raceResultRepo;

    public RaceResultService(RaceResultRepo raceResultRepo) {
        this.raceResultRepo = raceResultRepo;
    }

    public RaceResult save(RaceResult raceResult) {
        return raceResultRepo.save(raceResult);
    }

    public List<RaceResult> findAllByOrderByIdAsc() {
        return raceResultRepo.findAllByOrderByIdAsc();
    }

    public List<RaceResult> findByRaceIdOrderByPosition(Long id) {
        return raceResultRepo.findByRaceIdOrderByPosition(id);
    }

    public List<RaceResult> findBySeriesIdOrderByIdAsc(Long id) {
        return raceResultRepo.findBySeriesIdOrderByIdAsc(id);
    }

    public RaceResult findById(Long id) {
        return raceResultRepo.getOne(id);
    }
}
