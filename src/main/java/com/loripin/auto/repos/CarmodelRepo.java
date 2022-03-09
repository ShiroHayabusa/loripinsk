package com.loripin.auto.repos;

import com.loripin.auto.model.Carmodel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CarmodelRepo extends JpaRepository<Carmodel, Long> {
    List<Carmodel> findAllByOrderByIdAsc();

    List<Carmodel> findAllByOrderByManufacturerAsc();

    List<Carmodel> findByManufacturerId(Long id);

    List<Carmodel> findAllByOrderByNameAsc();

    List<Carmodel> findByManufacturerIdOrderByName(Long id);
}
