package com.loripin.auto.repos;

import com.loripin.auto.model.Track;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrackRepo extends JpaRepository<Track, Long> {
    List<Track> findAllByOrderByName();
}
