package com.loripin.auto.service;

import com.loripin.auto.model.Series;
import com.loripin.auto.repos.SeriesRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeriesService {
    private final
    SeriesRepo seriesRepo;

    public SeriesService(SeriesRepo seriesRepo) {
        this.seriesRepo = seriesRepo;
    }

    public Series save(Series series) {
        return seriesRepo.save(series);
    }

    public List<Series> findAll() {
        return seriesRepo.findAll();
    }

    public List<Series> findAllByOrderByIdAsc() {
        return seriesRepo.findAllByOrderByIdAsc();
    }

    public Series findById(Long id) {
        return seriesRepo.getOne(id);
    }

    public Series findByName(String name) {
        return seriesRepo.findByName(name);
    }

    public List<Series> findAllByOrderByName() {
        return seriesRepo.findAllByOrderByName();
    }
}
