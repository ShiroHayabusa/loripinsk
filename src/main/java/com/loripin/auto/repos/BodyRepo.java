package com.loripin.auto.repos;

import com.loripin.auto.model.Body;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BodyRepo  extends JpaRepository<Body, Long> {
    List<Body> findAllByOrderByIdAsc();

    List<Body> findAllByOrderByNameAsc();
}
