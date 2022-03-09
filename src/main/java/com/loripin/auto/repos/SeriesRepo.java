package com.loripin.auto.repos;

import com.loripin.auto.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeriesRepo extends JpaRepository<Series, Long> {
    List<Series> findAllByOrderByIdAsc();

    Series findByName(String name);

    List<Series> findAllByOrderByName();
}
