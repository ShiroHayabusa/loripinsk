package com.loripin.auto.repos;

import com.loripin.auto.model.Drive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriveRepo extends JpaRepository<Drive, Integer> {
    List<Drive> findAllByOrderByIdAsc();
}
