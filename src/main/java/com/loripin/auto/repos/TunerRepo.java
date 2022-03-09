package com.loripin.auto.repos;

import com.loripin.auto.model.Tuner;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TunerRepo extends JpaRepository<Tuner, Long> {
    List<Tuner> findAllByOrderByName();
}
