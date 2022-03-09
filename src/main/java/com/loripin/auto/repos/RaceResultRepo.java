package com.loripin.auto.repos;

import com.loripin.auto.model.RaceResult;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaceResultRepo extends JpaRepository<RaceResult, Long> {
    List<RaceResult> findAllByOrderByIdAsc();

    List<RaceResult> findByRaceIdOrderByPosition(Long id);

    List<RaceResult> findBySeriesIdOrderByIdAsc(Long id);
}
