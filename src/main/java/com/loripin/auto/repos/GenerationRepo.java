package com.loripin.auto.repos;

import com.loripin.auto.model.Generation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GenerationRepo extends JpaRepository<Generation, Long> {

    List<Generation> findAllByOrderByIdAsc();

    List<Generation> findByCarmodelIdOrderByNameAsc(Long id);

    List<Generation> findAllByOrderByManufacturerAsc();

    List<Generation> findByCarmodelIdOrderByYearsAsc(Long id);
}
