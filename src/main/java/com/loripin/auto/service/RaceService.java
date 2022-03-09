package com.loripin.auto.service;

import com.loripin.auto.model.Race;
import com.loripin.auto.repos.RaceRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RaceService {
    private final
    RaceRepo raceRepo;

    public RaceService(RaceRepo raceRepo) {
        this.raceRepo = raceRepo;
    }

    public Race save(Race race) {
        return raceRepo.save(race);
    }

    public List<Race> findAll() {
        return raceRepo.findAll();
    }

    public List<Race> findBySeriesIdAndSeasonOrderByStageNumber(Long id, String i) {
        return raceRepo.findBySeriesIdAndSeasonOrderByStageNumber(id, i);
    }

    public List<Race> findBySeriesNameAndSeasonOrderByStageNumber(String name, String s) {
        return raceRepo.findBySeriesNameAndSeasonOrderByStageNumber(name, s);
    }

    public Race findById(Long id) {
        return raceRepo.getOne(id);
    }
}
