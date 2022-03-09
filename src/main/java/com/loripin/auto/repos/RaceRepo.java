package com.loripin.auto.repos;

import com.loripin.auto.model.Race;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaceRepo extends JpaRepository<Race, Long> {
    List<Race> findBySeriesIdAndSeasonOrderByStageNumber(Long id, String i);

    List<Race> findBySeriesNameAndSeasonOrderByStageNumber(String name, String s);
}
