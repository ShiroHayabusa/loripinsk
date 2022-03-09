package com.loripin.auto.repos;

import com.loripin.auto.model.Spec;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SpecRepo extends JpaRepository<Spec, Long> {
    List<Spec> findAllByOrderByName();
}
