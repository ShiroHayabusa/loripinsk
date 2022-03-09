package com.loripin.auto.service;

import com.loripin.auto.model.Country;
import com.loripin.auto.model.Track;
import com.loripin.auto.repos.TrackRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TrackService {
    private final
    TrackRepo trackRepo;

    public TrackService(TrackRepo trackRepo) {
        this.trackRepo = trackRepo;
    }

    public Track save(Track track) {
        return trackRepo.save(track);
    }

    public List<Track> findAll() {
        return trackRepo.findAll();
    }

    public List<Track> findAllByOrderByName() {
        return trackRepo.findAllByOrderByName();
    }

    public Track findById(Long id) {
        return trackRepo.getOne(id);
    }
    public void deleteById(Long id) {
        trackRepo.deleteById(id);
    }
}
