package com.loripin.auto.repos;

import com.loripin.auto.model.Qualification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QualificationRepo extends JpaRepository<Qualification, Long> {
    List<Qualification> findByRaceIdOrderByPosition(Long id);
}
