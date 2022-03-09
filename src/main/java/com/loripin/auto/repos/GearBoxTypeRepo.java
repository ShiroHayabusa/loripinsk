package com.loripin.auto.repos;

import com.loripin.auto.model.GearBoxType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GearBoxTypeRepo extends JpaRepository<GearBoxType, Long> {
    List<GearBoxType> findAllByOrderByIdAsc();

    List<GearBoxType> findAllByOrderByNameAsc();
}
