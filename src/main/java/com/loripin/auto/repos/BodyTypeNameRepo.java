package com.loripin.auto.repos;

import com.loripin.auto.model.BodyTypeName;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BodyTypeNameRepo extends JpaRepository<BodyTypeName, Long> {
    List<BodyTypeName> findAllByOrderByIdAsc();

    List<BodyTypeName> findAllByOrderByNameAsc();
}
