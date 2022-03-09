package com.loripin.auto.repos;

import com.loripin.auto.model.RaceClass;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RaceClassRepo extends JpaRepository<RaceClass, Long> {
    List<RaceClass> findAllByOrderByIdAsc();
}
