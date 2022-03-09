package com.loripin.auto.repos;

import com.loripin.auto.model.Person;
import com.loripin.auto.model.Series;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PersonRepo extends JpaRepository<Person, Long> {
    List<Person> findAllByOrderByIdAsc();

    List<Person> findBySeriesIdOrderByIdAsc(Long id);

    List<Person> findBySeriesIdOrderByPoints2021Desc(Long id);

    List<Person> findAllByOrderByNameAsc();

    List<Person> findByTeamIdOrderByNameAsc(Long id);

    List<Person> findByTeamIdAndNavigatorOrderByNameAsc(Long id, Boolean b);

    List<Person> findBySeriesIdOrderByNameAsc(Long tmpSeries);
}
