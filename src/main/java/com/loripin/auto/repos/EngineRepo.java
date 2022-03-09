package com.loripin.auto.repos;

import com.loripin.auto.model.Engine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EngineRepo extends JpaRepository<Engine, Long> {
    List<Engine> findAllByOrderByIdAsc();

    List<Engine> findAllByOrderByNameAsc();
}
