package com.loripin.auto.repos;

import com.loripin.auto.model.Title;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TitleRepo extends JpaRepository<Title, Long> {
    List<Title> findAllByOrderByIdAsc();
}
