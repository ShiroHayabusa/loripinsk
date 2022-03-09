package com.loripin.auto.repos;

import com.loripin.auto.model.Modification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ModificationRepo extends JpaRepository<Modification, Long> {
    List<Modification> findAllByOrderByIdAsc();

    List<Modification> findByBodyTypeIdOrderByName(Long id);

    List<Modification> findByTunerId(Long id);

    List<Modification> findByTuner(String s);

    List<Modification> findAllByOrderByIdDesc();

    List<Modification> findBySeriesIdOrderByNameAsc(Long id);

    List<Modification> findByEngineIdOrderByIdDesc(Long id);
}
