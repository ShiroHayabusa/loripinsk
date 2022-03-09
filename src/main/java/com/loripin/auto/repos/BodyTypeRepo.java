package com.loripin.auto.repos;

import com.loripin.auto.model.BodyType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BodyTypeRepo extends JpaRepository<BodyType, Long> {
    List<BodyType> findAllByOrderByIdAsc();

    List<BodyType> findByGenerationIdAndRestyleName(Long id, String name);

    List<BodyType> findByGenerationIdAndRestyleNameOrderByBodyTypeName(Long id, String name);
}
