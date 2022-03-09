package com.loripin.auto.repos;

import com.loripin.auto.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CountryRepo extends JpaRepository<Country, Long> {
    List<Country> findAllByOrderByName();
}
