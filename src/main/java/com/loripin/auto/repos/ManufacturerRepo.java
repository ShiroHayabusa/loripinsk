package com.loripin.auto.repos;

import com.loripin.auto.model.Manufacturer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ManufacturerRepo extends JpaRepository<Manufacturer, Long> {

    List<Manufacturer> findAllByOrderByIdAsc();

    List<Manufacturer> findAllByOrderByNameAsc();
}
