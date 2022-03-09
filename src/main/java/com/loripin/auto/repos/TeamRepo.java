package com.loripin.auto.repos;

import com.loripin.auto.model.Series;
import com.loripin.auto.model.Team;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TeamRepo extends JpaRepository<Team, Long> {
    List<Team> findAllByOrderByIdAsc();

    List<Team> findBySeriesIdOrderByIdAsc(Long id);

    List<Team> findAllByOrderByNameAsc();

    List<Team> findBySeriesIdOrderByNameAsc(Long tmpSeries);

    List<Team> findBySeriesIdOrderByPoints2021Desc(Long id);
}
