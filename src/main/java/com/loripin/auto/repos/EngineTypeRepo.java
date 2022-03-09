package com.loripin.auto.repos;

import com.loripin.auto.model.EngineType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EngineTypeRepo extends JpaRepository<EngineType, Integer> {
    List<EngineType> findAllByOrderByIdAsc();

    List<EngineType> findAllByOrderByNameAsc();
}
