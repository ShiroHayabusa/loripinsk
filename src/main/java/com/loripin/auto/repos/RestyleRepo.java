package com.loripin.auto.repos;

import com.loripin.auto.model.Generation;
import com.loripin.auto.model.Restyle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RestyleRepo extends JpaRepository<Restyle, Long> {
    List<Restyle> findAllByOrderByIdAsc();

    List<Restyle> findAllByOrderByNameAsc();

    List<Restyle> findByGenerationIdOrderByNameAsc(Long id);
}
