package com.loripin.auto.repos;

import com.loripin.auto.model.Fuel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FuelRepo extends JpaRepository<Fuel, Integer> {
    List<Fuel> findAllByOrderByIdAsc();
}
