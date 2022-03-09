package com.loripin.auto.repos;

import com.loripin.auto.model.GearBox;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GearBoxRepo extends JpaRepository<GearBox, Long> {
    List<GearBox> findAllByOrderByIdAsc();

    List<GearBox> findAllByOrderByNameAsc();
}
